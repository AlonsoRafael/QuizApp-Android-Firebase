package com.alonsorafael.quizapp_android_firebase.data.repository

import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuestionDao
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuiz
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuizEntity
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuestion
import com.alonsorafael.quizapp_android_firebase.data.mapper.toQuestionEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz
import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val firestore: FirebaseFirestore
) : QuizRepository {

    override fun getAllQuizzes(): Flow<List<Quiz>> {
        return quizDao.getAllActiveQuizzes().map { entities ->
            entities.map { it.toQuiz() }
        }
    }

    override fun getQuizById(quizId: String): Flow<Quiz?> {
        return quizDao.getQuizById(quizId).map { entity ->
            entity?.toQuiz()
        }
    }

    override fun getQuizzesByCategory(category: String): Flow<List<Quiz>> {
        return quizDao.getQuizzesByCategory(category).map { entities ->
            entities.map { it.toQuiz() }
        }
    }

    override fun getQuizzesByDifficulty(difficulty: String): Flow<List<Quiz>> {
        return quizDao.getQuizzesByDifficulty(difficulty).map { entities ->
            entities.map { it.toQuiz() }
        }
    }

    override fun getQuestionsByQuizId(quizId: String): Flow<List<Question>> {
        return questionDao.getQuestionsByQuizId(quizId).map { entities ->
            entities.map { it.toQuestion() }
        }
    }

    override suspend fun syncQuizzesFromFirebase() {
        try {
            val snapshot = firestore.collection("quizzes").get().await()
            val quizzes = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Quiz::class.java)?.copy(id = doc.id)
            }
            
            if (quizzes.isNotEmpty()) {
                quizDao.insertQuizzes(quizzes.map { it.toQuizEntity() })
            }
        } catch (e: Exception) {
            // Handle error - could log or notify user
            throw e
        }
    }

    override suspend fun syncQuestionsFromFirebase(quizId: String) {
        try {
            val snapshot = firestore.collection("quizzes")
                .document(quizId)
                .collection("questions")
                .get()
                .await()
            
            val questions = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Question::class.java)?.copy(id = doc.id, quizId = quizId)
            }
            
            if (questions.isNotEmpty()) {
                questionDao.insertQuestions(questions.map { it.toQuestionEntity() })
            }
        } catch (e: Exception) {
            // Handle error - could log or notify user
            throw e
        }
    }

    override suspend fun insertQuiz(quiz: Quiz) {
        quizDao.insertQuiz(quiz.toQuizEntity())
    }

    override suspend fun insertQuestion(question: Question) {
        questionDao.insertQuestion(question.toQuestionEntity())
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        quizDao.updateQuiz(quiz.toQuizEntity())
    }

    override suspend fun deleteQuiz(quizId: String) {
        quizDao.deleteQuiz(Quiz(quizId, "", "", "", "", null, 0, 0).toQuizEntity())
        questionDao.deleteQuestionsByQuizId(quizId)
    }
}
