package com.alonsorafael.quizapp_android_firebase.domain.usecase.auth

import com.alonsorafael.quizapp_android_firebase.domain.model.User
import com.alonsorafael.quizapp_android_firebase.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, displayName: String): Result<User> {
        if (email.isBlank() || password.isBlank() || displayName.isBlank()) {
            return Result.failure(IllegalArgumentException("All fields are required"))
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format"))
        }
        
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters"))
        }
        
        if (displayName.length < 2) {
            return Result.failure(IllegalArgumentException("Display name must be at least 2 characters"))
        }
        
        return authRepository.signUpWithEmail(email, password, displayName)
    }
}
