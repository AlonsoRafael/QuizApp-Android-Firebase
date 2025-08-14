package com.alonsorafael.quizapp_android_firebase.domain.model

data class Question(
    val id: String,
    val quizId: String,
    val questionText: String,
    val questionType: QuestionType,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String?,
    val points: Int = 1,
    val order: Int = 0
)

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    TEXT
}
