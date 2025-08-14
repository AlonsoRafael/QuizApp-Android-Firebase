package com.alonsorafael.quizapp_android_firebase.domain.usecase.quizattempt

import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizAttemptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAttemptsUseCase @Inject constructor(
    private val quizAttemptRepository: QuizAttemptRepository
) {
    operator fun invoke(userId: String): Flow<List<QuizAttempt>> {
        if (userId.isBlank()) {
            throw IllegalArgumentException("User ID cannot be empty")
        }
        return quizAttemptRepository.getAttemptsByUserId(userId)
    }
}
