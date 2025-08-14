package com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz

import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionsByQuizIdUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(quizId: String): Flow<List<Question>> {
        if (quizId.isBlank()) {
            throw IllegalArgumentException("Quiz ID cannot be empty")
        }
        return quizRepository.getQuestionsByQuizId(quizId)
    }
}
