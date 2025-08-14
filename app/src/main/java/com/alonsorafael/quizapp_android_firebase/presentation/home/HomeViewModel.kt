package com.alonsorafael.quizapp_android_firebase.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz.GetQuizzesUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz.SyncQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
    private val syncQuizzesUseCase: SyncQuizzesUseCase
) : ViewModel() {

    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    val quizzes: StateFlow<List<Quiz>> = _quizzes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadQuizzes()
    }

    fun loadQuizzes() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // First try to sync from Firebase
                try {
                    syncQuizzesUseCase()
                } catch (e: Exception) {
                    // If sync fails, continue with local data
                }
                
                // Then get quizzes from local database
                getQuizzesUseCase().collect { quizList ->
                    _quizzes.value = quizList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load quizzes"
                _isLoading.value = false
            }
        }
    }
}
