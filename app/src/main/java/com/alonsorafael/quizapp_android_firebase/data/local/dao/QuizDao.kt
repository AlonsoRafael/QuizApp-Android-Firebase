package com.alonsorafael.quizapp_android_firebase.data.local.dao

import androidx.room.*
import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    
    @Query("SELECT * FROM quizzes WHERE isActive = 1 ORDER BY title ASC")
    fun getAllActiveQuizzes(): Flow<List<QuizEntity>>
    
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizById(quizId: String): Flow<QuizEntity?>
    
    @Query("SELECT * FROM quizzes WHERE category = :category AND isActive = 1")
    fun getQuizzesByCategory(category: String): Flow<List<QuizEntity>>
    
    @Query("SELECT * FROM quizzes WHERE difficulty = :difficulty AND isActive = 1")
    fun getQuizzesByDifficulty(difficulty: String): Flow<List<QuizEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: QuizEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<QuizEntity>)
    
    @Update
    suspend fun updateQuiz(quiz: QuizEntity)
    
    @Delete
    suspend fun deleteQuiz(quiz: QuizEntity)
    
    @Query("DELETE FROM quizzes")
    suspend fun deleteAllQuizzes()
}
