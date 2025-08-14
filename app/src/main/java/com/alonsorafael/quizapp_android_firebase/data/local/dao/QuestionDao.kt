package com.alonsorafael.quizapp_android_firebase.data.local.dao

import androidx.room.*
import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    
    @Query("SELECT * FROM questions WHERE quizId = :quizId ORDER BY `order` ASC")
    fun getQuestionsByQuizId(quizId: String): Flow<List<QuestionEntity>>
    
    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionById(questionId: String): Flow<QuestionEntity?>
    
    @Query("SELECT COUNT(*) FROM questions WHERE quizId = :quizId")
    fun getQuestionCountByQuizId(quizId: String): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)
    
    @Update
    suspend fun updateQuestion(question: QuestionEntity)
    
    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)
    
    @Query("DELETE FROM questions WHERE quizId = :quizId")
    suspend fun deleteQuestionsByQuizId(quizId: String)
    
    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()
}
