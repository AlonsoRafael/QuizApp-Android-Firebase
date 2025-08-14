package com.alonsorafael.quizapp_android_firebase.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quizattempt.GetUserAttemptsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserAttemptsUseCase: GetUserAttemptsUseCase
) : ViewModel() {

    private val _attempts = MutableStateFlow<List<QuizAttempt>>(emptyList())
    val attempts: StateFlow<List<QuizAttempt>> = _attempts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _statistics = MutableStateFlow(HistoryStatistics())
    val statistics: StateFlow<HistoryStatistics> = _statistics.asStateFlow()

    fun loadHistory() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // TODO: Get current user ID from auth repository
                val userId = "temp_user_id"
                
                getUserAttemptsUseCase(userId).collect { attemptsList ->
                    _attempts.value = attemptsList
                    calculateStatistics(attemptsList)
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load history"
                _isLoading.value = false
            }
        }
    }

    private fun calculateStatistics(attempts: List<QuizAttempt>) {
        if (attempts.isEmpty()) {
            _statistics.value = HistoryStatistics()
            return
        }

        val totalQuizzes = attempts.size
        val totalScore = attempts.sumOf { it.score }
        val totalMaxScore = attempts.sumOf { it.maxScore }
        val averageScore = if (totalMaxScore > 0) {
            (totalScore.toDouble() / totalMaxScore.toDouble()) * 100
        } else 0.0

        val bestScore = attempts.maxOfOrNull { attempt ->
            (attempt.score.toDouble() / attempt.maxScore.toDouble()) * 100
        } ?: 0.0

        _statistics.value = HistoryStatistics(
            totalQuizzes = totalQuizzes,
            averageScore = averageScore,
            bestScore = bestScore
        )
    }
}

data class HistoryStatistics(
    val totalQuizzes: Int = 0,
    val averageScore: Double = 0.0,
    val bestScore: Double = 0.0
)
