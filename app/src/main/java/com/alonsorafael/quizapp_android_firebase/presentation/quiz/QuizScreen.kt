package com.alonsorafael.quizapp_android_firebase.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.model.QuestionType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quizId: String,
    onNavigateToResults: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val quizState by viewModel.quizState.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val isQuizCompleted by viewModel.isQuizCompleted.collectAsState()

    LaunchedEffect(quizId) {
        viewModel.loadQuiz(quizId)
    }

    LaunchedEffect(isQuizCompleted) {
        if (isQuizCompleted) {
            delay(1000) // Show results for 1 second
            onNavigateToResults(quizId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (quizState) {
                is QuizState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is QuizState.Success -> {
                    val quiz = quizState.quiz
                    val questions = quizState.questions
                    
                    if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
                        QuizContent(
                            quiz = quiz,
                            questions = questions,
                            currentQuestionIndex = currentQuestionIndex,
                            answers = answers,
                            timeRemaining = timeRemaining,
                            onAnswerSelected = { questionId, answer ->
                                viewModel.selectAnswer(questionId, answer)
                            },
                            onNextQuestion = {
                                if (currentQuestionIndex < questions.size - 1) {
                                    viewModel.nextQuestion()
                                } else {
                                    viewModel.completeQuiz()
                                }
                            },
                            onPreviousQuestion = {
                                if (currentQuestionIndex > 0) {
                                    viewModel.previousQuestion()
                                }
                            }
                        )
                    }
                }
                is QuizState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: ${quizState.message}",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadQuiz(quizId) }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizContent(
    quiz: com.alonsorafael.quizapp_android_firebase.domain.model.Quiz,
    questions: List<Question>,
    currentQuestionIndex: Int,
    answers: Map<String, String>,
    timeRemaining: Long,
    onAnswerSelected: (String, String) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit
) {
    val currentQuestion = questions[currentQuestionIndex]
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Progress and timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            quiz.timeLimit?.let {
                Text(
                    text = "Time: ${timeRemaining / 60}:${(timeRemaining % 60).toString().padStart(2, '0')}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (timeRemaining < 60) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Question
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = currentQuestion.questionText,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Answer options
                when (currentQuestion.questionType) {
                    QuestionType.MULTIPLE_CHOICE -> {
                        currentQuestion.options.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = answers[currentQuestion.id] == option,
                                    onClick = { onAnswerSelected(currentQuestion.id, option) }
                                )
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    QuestionType.TRUE_FALSE -> {
                        listOf("True", "False").forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = answers[currentQuestion.id] == option,
                                    onClick = { onAnswerSelected(currentQuestion.id, option) }
                                )
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    QuestionType.TEXT -> {
                        OutlinedTextField(
                            value = answers[currentQuestion.id] ?: "",
                            onValueChange = { onAnswerSelected(currentQuestion.id, it) },
                            label = { Text("Your answer") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onPreviousQuestion,
                enabled = currentQuestionIndex > 0
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Previous")
            }
            
            Button(
                onClick = onNextQuestion,
                enabled = answers.containsKey(currentQuestion.id)
            ) {
                Text(
                    if (currentQuestionIndex < questions.size - 1) "Next" else "Finish"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}
