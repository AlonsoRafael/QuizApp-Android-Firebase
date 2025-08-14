package com.alonsorafael.quizapp_android_firebase.data.local.dao

import androidx.room.*
import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuizAttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {
    
    @Query("SELECT * FROM quiz_attempts WHERE userId = :userId ORDER BY startTime DESC")
    fun getAttemptsByUserId(userId: String): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT * FROM quiz_attempts WHERE quizId = :quizId ORDER BY score DESC LIMIT 10")
    fun getTopAttemptsByQuizId(quizId: String): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT * FROM quiz_attempts WHERE userId = :userId AND quizId = :quizId ORDER BY startTime DESC")
    fun getAttemptsByUserAndQuiz(userId: String, quizId: String): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT * FROM quiz_attempts WHERE id = :attemptId")
    fun getAttemptById(attemptId: String): Flow<QuizAttemptEntity?>
    
    @Query("SELECT COUNT(*) FROM quiz_attempts WHERE userId = :userId")
    fun getAttemptCountByUserId(userId: String): Flow<Int>
    
    @Query("SELECT AVG(score) FROM quiz_attempts WHERE userId = :userId AND isCompleted = 1")
    fun getAverageScoreByUserId(userId: String): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuizAttemptEntity)
    
    @Update
    suspend fun updateAttempt(attempt: QuizAttemptEntity)
    
    @Delete
    suspend fun deleteAttempt(attempt: QuizAttemptEntity)
    
    @Query("DELETE FROM quiz_attempts WHERE userId = :userId")
    suspend fun deleteAttemptsByUserId(userId: String)
}
