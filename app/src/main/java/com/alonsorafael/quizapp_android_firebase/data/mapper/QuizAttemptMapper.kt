package com.alonsorafael.quizapp_android_firebase.data.mapper

import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuizAttemptEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun QuizAttemptEntity.toQuizAttempt(): QuizAttempt {
    val gson = Gson()
    val answers = try {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        gson.fromJson<Map<String, String>>(this.answers, mapType) ?: emptyMap()
    } catch (e: Exception) {
        emptyMap()
    }
    
    return QuizAttempt(
        id = id,
        userId = userId,
        quizId = quizId,
        startTime = startTime,
        endTime = endTime,
        score = score,
        maxScore = maxScore,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        timeSpent = timeSpent,
        isCompleted = isCompleted,
        answers = answers
    )
}

fun QuizAttempt.toQuizAttemptEntity(): QuizAttemptEntity {
    val gson = Gson()
    val answersJson = gson.toJson(answers)
    
    return QuizAttemptEntity(
        id = id,
        userId = userId,
        quizId = quizId,
        startTime = startTime,
        endTime = endTime,
        score = score,
        maxScore = maxScore,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        timeSpent = timeSpent,
        isCompleted = isCompleted,
        answers = answersJson
    )
}
