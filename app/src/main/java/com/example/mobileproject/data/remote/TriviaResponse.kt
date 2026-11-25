package com.example.mobileproject.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponse(
    @SerialName("response_code") val responseCode: Int,
    val results: List<TriviaQuestionDto>
)

@Serializable
data class TriviaQuestionDto(
    val question: String,
    @SerialName("correct_answer") val correctAnswer: String,
    @SerialName("incorrect_answers") val incorrectAnswers: List<String>
)
