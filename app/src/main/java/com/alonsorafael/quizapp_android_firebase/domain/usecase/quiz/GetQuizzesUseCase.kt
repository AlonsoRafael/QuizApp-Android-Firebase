package com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz

import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizzesUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(): Flow<List<Quiz>> {
        return quizRepository.getAllQuizzes()
    }
}
