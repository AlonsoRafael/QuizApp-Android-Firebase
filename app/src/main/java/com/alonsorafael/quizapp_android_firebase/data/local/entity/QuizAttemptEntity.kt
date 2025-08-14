package com.alonsorafael.quizapp_android_firebase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey
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
    val answers: String // JSON string of user answers
)
