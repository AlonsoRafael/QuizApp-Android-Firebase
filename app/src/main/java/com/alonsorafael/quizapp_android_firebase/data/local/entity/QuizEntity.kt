package com.alonsorafael.quizapp_android_firebase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val category: String,
    val difficulty: String,
    val timeLimit: Int?, // in minutes, null for no limit
    val questionCount: Int,
    val totalPoints: Int,
    val isActive: Boolean = true,
    val lastUpdated: Long = System.currentTimeMillis()
)
