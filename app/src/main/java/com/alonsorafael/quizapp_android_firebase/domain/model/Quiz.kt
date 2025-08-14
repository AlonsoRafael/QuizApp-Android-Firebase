package com.alonsorafael.quizapp_android_firebase.domain.model

data class Quiz(
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
