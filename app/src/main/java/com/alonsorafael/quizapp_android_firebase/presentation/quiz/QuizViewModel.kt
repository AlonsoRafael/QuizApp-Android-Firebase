package com.alonsorafael.quizapp_android_firebase.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz.GetQuizByIdUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz.GetQuestionsByQuizIdUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz.SyncQuizzesUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quizattempt.SubmitQuizAttemptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizByIdUseCase: GetQuizByIdUseCase,
    private val getQuestionsByQuizIdUseCase: GetQuestionsByQuizIdUseCase,
    private val syncQuizzesUseCase: SyncQuizzesUseCase,
    private val submitQuizAttemptUseCase: SubmitQuizAttemptUseCase
) : ViewModel() {

    private val _quizState = MutableStateFlow<QuizState>(QuizState.Loading)
    val quizState: StateFlow<QuizState> = _quizState.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers: StateFlow<Map<String, String>> = _answers.asStateFlow()

    private val _timeRemaining = MutableStateFlow(0L)
    val timeRemaining: StateFlow<Long> = _timeRemaining.asStateFlow()

    private val _isQuizCompleted = MutableStateFlow(false)
    val isQuizCompleted: StateFlow<Boolean> = _isQuizCompleted.asStateFlow()

    private var quizStartTime: Long = 0L
    private var currentQuiz: Quiz? = null
    private var currentQuestions: List<Question> = emptyList()

    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            try {
                _quizState.value = QuizState.Loading
                
                // Sync questions from Firebase
                try {
                    syncQuizzesUseCase()
                } catch (e: Exception) {
                    // Continue with local data if sync fails
                }
                
                // Get quiz and questions
                getQuizByIdUseCase(quizId).collect { quiz ->
                    quiz?.let { quizData ->
                        currentQuiz = quizData
                        getQuestionsByQuizIdUseCase(quizId).collect { questions ->
                            currentQuestions = questions
                            if (questions.isNotEmpty()) {
                                _quizState.value = QuizState.Success(quizData, questions)
                                startQuiz(quizData)
                            } else {
                                _quizState.value = QuizState.Error("No questions found for this quiz")
                            }
                        }
                    } ?: run {
                        _quizState.value = QuizState.Error("Quiz not found")
                    }
                }
            } catch (e: Exception) {
                _quizState.value = QuizState.Error(e.message ?: "Failed to load quiz")
            }
        }
    }

    private fun startQuiz(quiz: Quiz) {
        quizStartTime = System.currentTimeMillis()
        quiz.timeLimit?.let { timeLimit ->
            _timeRemaining.value = timeLimit * 60L // Convert to seconds
            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timeRemaining.value > 0 && !_isQuizCompleted.value) {
                kotlinx.coroutines.delay(1000)
                _timeRemaining.value = _timeRemaining.value - 1
                
                if (_timeRemaining.value <= 0) {
                    completeQuiz()
                    break
                }
            }
        }
    }

    fun selectAnswer(questionId: String, answer: String) {
        val currentAnswers = _answers.value.toMutableMap()
        currentAnswers[questionId] = answer
        _answers.value = currentAnswers
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < currentQuestions.size - 1) {
            _currentQuestionIndex.value = _currentQuestionIndex.value + 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value = _currentQuestionIndex.value - 1
        }
    }

    fun completeQuiz() {
        if (_isQuizCompleted.value) return
        
        _isQuizCompleted.value = true
        val endTime = System.currentTimeMillis()
        
        // Submit quiz attempt
        viewModelScope.launch {
            currentQuiz?.let { quiz ->
                // TODO: Get current user ID from auth repository
                val userId = "temp_user_id"
                
                submitQuizAttemptUseCase(
                    userId = userId,
                    quizId = quiz.id,
                    questions = currentQuestions,
                    answers = _answers.value,
                    startTime = quizStartTime,
                    endTime = endTime
                )
            }
        }
    }
}

sealed class QuizState {
    object Loading : QuizState()
    data class Success(
        val quiz: Quiz,
        val questions: List<Question>
    ) : QuizState()
    data class Error(val message: String) : QuizState()
}
