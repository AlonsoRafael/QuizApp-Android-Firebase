package com.alonsorafael.quizapp_android_firebase.domain.usecase.auth

import com.alonsorafael.quizapp_android_firebase.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}
