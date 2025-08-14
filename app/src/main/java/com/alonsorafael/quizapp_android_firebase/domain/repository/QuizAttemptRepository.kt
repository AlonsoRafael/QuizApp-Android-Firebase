package com.alonsorafael.quizapp_android_firebase.domain.repository

import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import kotlinx.coroutines.flow.Flow

interface QuizAttemptRepository {
    fun getAttemptsByUserId(userId: String): Flow<List<QuizAttempt>>
    fun getAttemptsByUserAndQuiz(userId: String, quizId: String): Flow<List<QuizAttempt>>
    fun getTopAttemptsByQuizId(quizId: String): Flow<List<QuizAttempt>>
    fun getAttemptById(attemptId: String): Flow<QuizAttempt?>
    
    suspend fun insertAttempt(attempt: QuizAttempt): Result<Unit>
    suspend fun updateAttempt(attempt: QuizAttempt): Result<Unit>
    suspend fun deleteAttempt(attemptId: String): Result<Unit>
    suspend fun syncAttemptsToFirebase(userId: String)
    suspend fun syncAttemptsFromFirebase(userId: String)
}
