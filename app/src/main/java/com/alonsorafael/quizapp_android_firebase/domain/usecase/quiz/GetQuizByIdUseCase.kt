package com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz

import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizByIdUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(quizId: String): Flow<Quiz?> {
        if (quizId.isBlank()) {
            throw IllegalArgumentException("Quiz ID cannot be empty")
        }
        return quizRepository.getQuizById(quizId)
    }
}
