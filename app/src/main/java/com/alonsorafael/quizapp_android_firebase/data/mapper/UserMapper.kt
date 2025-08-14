package com.alonsorafael.quizapp_android_firebase.data.mapper

import com.alonsorafael.quizapp_android_firebase.data.local.entity.UserEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        totalQuizzes = totalQuizzes,
        totalScore = totalScore,
        averageScore = averageScore,
        lastLogin = lastLogin
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        totalQuizzes = totalQuizzes,
        totalScore = totalScore,
        averageScore = averageScore,
        lastLogin = lastLogin
    )
}
