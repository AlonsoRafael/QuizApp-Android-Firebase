package com.alonsorafael.quizapp_android_firebase.domain.usecase.quizattempt

import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizAttemptRepository
import java.util.UUID
import javax.inject.Inject

class SubmitQuizAttemptUseCase @Inject constructor(
    private val quizAttemptRepository: QuizAttemptRepository
) {
    suspend operator fun invoke(
        userId: String,
        quizId: String,
        questions: List<Question>,
        answers: Map<String, String>,
        startTime: Long,
        endTime: Long
    ): Result<Unit> {
        if (userId.isBlank() || quizId.isBlank()) {
            return Result.failure(IllegalArgumentException("User ID and Quiz ID cannot be empty"))
        }
        
        if (questions.isEmpty()) {
            return Result.failure(IllegalArgumentException("Questions list cannot be empty"))
        }
        
        val correctAnswers = questions.count { question ->
            answers[question.id] == question.correctAnswer
        }
        
        val totalPoints = questions.sumOf { it.points }
        val score = questions.sumOf { question ->
            if (answers[question.id] == question.correctAnswer) question.points else 0
        }
        
        val timeSpent = endTime - startTime
        
        val attempt = QuizAttempt(
            id = UUID.randomUUID().toString(),
            userId = userId,
            quizId = quizId,
            startTime = startTime,
            endTime = endTime,
            score = score,
            maxScore = totalPoints,
            correctAnswers = correctAnswers,
            totalQuestions = questions.size,
            timeSpent = timeSpent,
            isCompleted = true,
            answers = answers
        )
        
        return quizAttemptRepository.insertAttempt(attempt)
    }
}
