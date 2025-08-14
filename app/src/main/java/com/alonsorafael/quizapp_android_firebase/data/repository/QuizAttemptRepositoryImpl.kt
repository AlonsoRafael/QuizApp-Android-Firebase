package com.alonsorafael.quizapp_android_firebase.data.repository

import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizAttemptDao
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuizAttempt
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuizAttemptEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizAttemptRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizAttemptRepositoryImpl @Inject constructor(
    private val quizAttemptDao: QuizAttemptDao,
    private val firestore: FirebaseFirestore
) : QuizAttemptRepository {

    override fun getAttemptsByUserId(userId: String): Flow<List<QuizAttempt>> {
        return quizAttemptDao.getAttemptsByUserId(userId).map { entities ->
            entities.map { it.toQuizAttempt() }
        }
    }

    override fun getAttemptsByUserAndQuiz(userId: String, quizId: String): Flow<List<QuizAttempt>> {
        return quizAttemptDao.getAttemptsByUserAndQuiz(userId, quizId).map { entities ->
            entities.map { it.toQuizAttempt() }
        }
    }

    override fun getTopAttemptsByQuizId(quizId: String): Flow<List<QuizAttempt>> {
        return quizAttemptDao.getTopAttemptsByQuizId(quizId).map { entities ->
            entities.map { it.toQuizAttempt() }
        }
    }

    override fun getAttemptById(attemptId: String): Flow<QuizAttempt?> {
        return quizAttemptDao.getAttemptById(attemptId).map { entity ->
            entity?.toQuizAttempt()
        }
    }

    override suspend fun insertAttempt(attempt: QuizAttempt): Result<Unit> {
        return try {
            quizAttemptDao.insertAttempt(attempt.toQuizAttemptEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAttempt(attempt: QuizAttempt): Result<Unit> {
        return try {
            quizAttemptDao.updateAttempt(attempt.toQuizAttemptEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAttempt(attemptId: String): Result<Unit> {
        return try {
            val attempt = quizAttemptDao.getAttemptById(attemptId).map { it?.toQuizAttempt() }
            attempt.collect { quizAttempt ->
                quizAttempt?.let {
                    quizAttemptDao.deleteAttempt(it.toQuizAttemptEntity())
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncAttemptsToFirebase(userId: String) {
        try {
            val attempts = quizAttemptDao.getAttemptsByUserId(userId).map { entities ->
                entities.map { it.toQuizAttempt() }
            }
            
            attempts.collect { attemptList ->
                attemptList.forEach { attempt ->
                    firestore.collection("users")
                        .document(userId)
                        .collection("attempts")
                        .document(attempt.id)
                        .set(attempt)
                        .await()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun syncAttemptsFromFirebase(userId: String) {
        try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("attempts")
                .get()
                .await()
            
            val attempts = snapshot.documents.mapNotNull { doc ->
                doc.toObject(QuizAttempt::class.java)?.copy(id = doc.id)
            }
            
            attempts.forEach { attempt ->
                quizAttemptDao.insertAttempt(attempt.toQuizAttemptEntity())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
