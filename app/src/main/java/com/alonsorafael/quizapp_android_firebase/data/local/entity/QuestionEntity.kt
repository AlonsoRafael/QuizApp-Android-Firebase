package com.alonsorafael.quizapp_android_firebase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val quizId: String,
    val questionText: String,
    val questionType: String, // "multiple_choice", "true_false", "text"
    val options: List<String>, // JSON string for multiple choice options
    val correctAnswer: String,
    val explanation: String?,
    val points: Int = 1,
    val order: Int = 0
)
