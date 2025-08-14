package com.alonsorafael.quizapp_android_firebase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val uid: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val totalQuizzes: Int = 0,
    val totalScore: Int = 0,
    val averageScore: Double = 0.0,
    val lastLogin: Long = System.currentTimeMillis()
)
