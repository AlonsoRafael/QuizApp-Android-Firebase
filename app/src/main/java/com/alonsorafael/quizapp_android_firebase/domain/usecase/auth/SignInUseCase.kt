package com.alonsorafael.quizapp_android_firebase.domain.usecase.auth

import com.alonsorafael.quizapp_android_firebase.domain.model.User
import com.alonsorafael.quizapp_android_firebase.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Invalid email format"))
        }
        
        return authRepository.signInWithEmail(email, password)
    }
}
