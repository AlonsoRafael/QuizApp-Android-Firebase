package com.alonsorafael.quizapp_android_firebase.data.mapper

import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuizEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.Quiz

fun QuizEntity.toQuiz(): Quiz {
    return Quiz(
        id = id,
        title = title,
        description = description,
        category = category,
        difficulty = difficulty,
        timeLimit = timeLimit,
        questionCount = questionCount,
        totalPoints = totalPoints,
        isActive = isActive,
        lastUpdated = lastUpdated
    )
}

fun Quiz.toQuizEntity(): QuizEntity {
    return QuizEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        difficulty = difficulty,
        timeLimit = timeLimit,
        questionCount = questionCount,
        totalPoints = totalPoints,
        isActive = isActive,
        lastUpdated = lastUpdated
    )
}
