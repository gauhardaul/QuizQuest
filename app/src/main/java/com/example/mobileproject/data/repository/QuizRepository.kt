package com.example.mobileproject.data.repository

import com.example.mobileproject.data.Question
import com.example.mobileproject.data.remote.RetrofitInstance
import com.example.mobileproject.data.remote.TriviaApiService
import retrofit2.HttpException
import java.io.IOException
import java.net.URLDecoder

const val QUESTIONS_PER_QUIZ = 3

private const val RATE_LIMIT_MESSAGE = "Too many requests. Wait a few seconds and try again."

class QuizRepository(
    private val api: TriviaApiService = RetrofitInstance.triviaApi
) {
    suspend fun getQuestions(topic: String): List<Question> {
        val category = when (topic) {
            "Math" -> 19
            "IT" -> 18
            "Science" -> 17
            else -> throw IllegalArgumentException("Unknown quiz topic: $topic")
        }

        val response = try {
            api.getQuestions(amount = QUESTIONS_PER_QUIZ, category = category)
        } catch (error: HttpException) {
            if (error.code() == 429) throw IOException(RATE_LIMIT_MESSAGE, error)
            throw error
        }
        return when (response.responseCode) {
            0 -> response.results.map { item ->
                val text = decode(item.question)
                val correct = decode(item.correctAnswer)
                val options = item.incorrectAnswers.map(::decode) + correct
                if (text.isBlank() || options.size != 4 ||
                    options.any { it.isBlank() } || options.toSet().size != 4
                ) {
                    throw IOException("Trivia service returned an invalid question.")
                }

                Question(
                    text = text,
                    options = options.shuffled(),
                    correctAnswer = correct
                )
            }
            1 -> emptyList()
            5 -> throw IOException(RATE_LIMIT_MESSAGE)
            else -> throw IOException("Trivia service returned code ${response.responseCode}.")
        }
    }

    private fun decode(value: String): String =
        URLDecoder.decode(value.replace("+", "%2B"), "UTF-8")
}
