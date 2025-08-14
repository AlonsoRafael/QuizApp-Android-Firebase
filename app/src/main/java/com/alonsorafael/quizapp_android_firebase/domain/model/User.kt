package com.alonsorafael.quizapp_android_firebase.domain.model

data class User(
    val uid: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val totalQuizzes: Int = 0,
    val totalScore: Int = 0,
    val averageScore: Double = 0.0,
    val lastLogin: Long = System.currentTimeMillis()
)
