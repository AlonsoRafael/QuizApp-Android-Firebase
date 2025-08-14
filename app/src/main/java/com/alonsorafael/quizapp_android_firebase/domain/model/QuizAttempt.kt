package com.alonsorafael.quizapp_android_firebase.domain.model

data class QuizAttempt(
    val id: String,
    val userId: String,
    val quizId: String,
    val startTime: Long,
    val endTime: Long?,
    val score: Int = 0,
    val maxScore: Int,
    val correctAnswers: Int = 0,
    val totalQuestions: Int,
    val timeSpent: Long = 0, // in milliseconds
    val isCompleted: Boolean = false,
    val answers: Map<String, String> = emptyMap() // questionId to answer
)
