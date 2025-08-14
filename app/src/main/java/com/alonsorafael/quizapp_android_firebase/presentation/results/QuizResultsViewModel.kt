package com.alonsorafael.quizapp_android_firebase.presentation.results

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
class QuizResultsViewModel @Inject constructor(
    private val getUserAttemptsUseCase: GetUserAttemptsUseCase
) : ViewModel() {

    private val _attempt = MutableStateFlow<QuizAttempt?>(null)
    val attempt: StateFlow<QuizAttempt?> = _attempt.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadResults(quizId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // TODO: Get current user ID from auth repository
                val userId = "temp_user_id"
                
                getUserAttemptsUseCase(userId).collect { attempts ->
                    val quizAttempt = attempts.find { it.quizId == quizId }
                    if (quizAttempt != null) {
                        _attempt.value = quizAttempt
                    } else {
                        _error.value = "Quiz results not found"
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load results"
                _isLoading.value = false
            }
        }
    }
}
