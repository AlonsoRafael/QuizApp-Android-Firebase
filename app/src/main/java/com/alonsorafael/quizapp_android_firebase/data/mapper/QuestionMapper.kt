package com.alonsorafael.quizapp_android_firebase.data.mapper

import com.alonsorafael.quizapp_android_firebase.data.local.entity.QuestionEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.Question
import com.alonsorafael.quizapp_android_firebase.domain.model.QuestionType

fun QuestionEntity.toQuestion(): Question {
    return Question(
        id = id,
        quizId = quizId,
        questionText = questionText,
        questionType = when (questionType) {
            "multiple_choice" -> QuestionType.MULTIPLE_CHOICE
            "true_false" -> QuestionType.TRUE_FALSE
            "text" -> QuestionType.TEXT
            else -> QuestionType.MULTIPLE_CHOICE
        },
        options = options,
        correctAnswer = correctAnswer,
        explanation = explanation,
        points = points,
        order = order
    )
}

fun Question.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        quizId = quizId,
        questionText = questionText,
        questionType = when (questionType) {
            QuestionType.MULTIPLE_CHOICE -> "multiple_choice"
            QuestionType.TRUE_FALSE -> "true_false"
            QuestionType.TEXT -> "text"
        },
        options = options,
        correctAnswer = correctAnswer,
        explanation = explanation,
        points = points,
        order = order
    )
}
