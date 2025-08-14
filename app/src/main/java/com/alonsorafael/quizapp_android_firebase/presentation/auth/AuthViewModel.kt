package com.alonsorafael.quizapp_android_firebase.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonsorafael.quizapp_android_firebase.domain.usecase.auth.SignInUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.auth.SignUpUseCase
import com.alonsorafael.quizapp_android_firebase.domain.usecase.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = signInUseCase(email, password)
            result.fold(
                onSuccess = { user ->
                    _authState.value = AuthState.Success(user)
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
                }
            )
        }
    }

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = signUpUseCase(email, password, displayName)
            result.fold(
                onSuccess = { user ->
                    _authState.value = AuthState.Success(user)
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
                }
            )
        }
    }

    fun signInWithGoogle() {
        // TODO: Implement Google Sign In
        _authState.value = AuthState.Error("Google Sign In not implemented yet")
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _authState.value = AuthState.Idle
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: com.alonsorafael.quizapp_android_firebase.domain.model.User) : AuthState()
    data class Error(val message: String) : AuthState()
}
