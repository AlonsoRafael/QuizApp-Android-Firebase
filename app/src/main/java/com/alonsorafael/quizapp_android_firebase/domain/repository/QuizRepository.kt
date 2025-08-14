package com.alonsorafael.quizapp_android_firebase.domain.repository

import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getAllQuizzes(): Flow<List<Quiz>>
    fun getQuizById(quizId: String): Flow<Quiz?>
    fun getQuizzesByCategory(category: String): Flow<List<Quiz>>
    fun getQuizzesByDifficulty(difficulty: String): Flow<List<Quiz>>
    fun getQuestionsByQuizId(quizId: String): Flow<List<Question>>
    
    suspend fun syncQuizzesFromFirebase()
    suspend fun syncQuestionsFromFirebase(quizId: String)
    suspend fun insertQuiz(quiz: Quiz)
    suspend fun insertQuestion(question: Question)
    suspend fun updateQuiz(quiz: Quiz)
    suspend fun deleteQuiz(quizId: String)
}
