package com.alonsorafael.quizapp_android_firebase.domain.usecase.quiz

import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import javax.inject.Inject

class SyncQuizzesUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke() {
        quizRepository.syncQuizzesFromFirebase()
    }
}
