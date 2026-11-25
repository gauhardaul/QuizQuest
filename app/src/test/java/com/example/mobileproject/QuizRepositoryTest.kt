package com.example.mobileproject

import com.example.mobileproject.data.remote.TriviaApiService
import com.example.mobileproject.data.remote.TriviaQuestionDto
import com.example.mobileproject.data.remote.TriviaResponse
import com.example.mobileproject.data.repository.QuizRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class QuizRepositoryTest {
    @Test
    fun requestsEachCategoryAndDecodesAnswers() = runBlocking {
        val api = FakeTriviaApi(TriviaResponse(0, listOf(
            TriviaQuestionDto("What%20is%202%20%2B%202%3F", "4", listOf("3", "5", "6"))
        )))
        val repository = QuizRepository(api)

        for ((topic, category) in mapOf("Math" to 19, "IT" to 18, "Science" to 17)) {
            val question = repository.getQuestions(topic).single()
            assertEquals(listOf(3, category), api.request)
            assertEquals("What is 2 + 2?", question.text)
            assertEquals("4", question.correctAnswer)
            assertEquals(4, question.options.size)
            assertEquals(setOf("3", "4", "5", "6"), question.options.toSet())
        }
    }

    @Test
    fun noResultsReturnsAnEmptyList() = runBlocking {
        val repository = QuizRepository(FakeTriviaApi(TriviaResponse(1, emptyList())))
        assertTrue(repository.getQuestions("Math").isEmpty())
    }

    @Test
    fun rateLimitIsReportedAsAnError() = runBlocking {
        val repository = QuizRepository(FakeTriviaApi(TriviaResponse(5, emptyList())))
        val error = runCatching { repository.getQuestions("Math") }.exceptionOrNull()
        assertTrue(error is IOException)
    }

    @Test
    fun httpRateLimitHasTheSameRetryMessageAsTheApiCode() = runBlocking {
        val httpError = HttpException(Response.error<TriviaResponse>(429, ResponseBody.create(null, "")))
        val repository = QuizRepository(FakeTriviaApi(TriviaResponse(0, emptyList()), httpError))
        val error = runCatching { repository.getQuestions("Math") }.exceptionOrNull()

        assertTrue(error is IOException)
        assertEquals("Too many requests. Wait a few seconds and try again.", error?.message)
        assertEquals(httpError, error?.cause)
    }

    @Test
    fun rejectsAQuestionWithDuplicateOptionsAfterDecoding() = runBlocking {
        val response = TriviaResponse(0, listOf(
            TriviaQuestionDto("What%20is%202%20%2B%202%3F", "4", listOf("%34", "5", "6"))
        ))
        val repository = QuizRepository(FakeTriviaApi(response))
        val error = runCatching { repository.getQuestions("Math") }.exceptionOrNull()

        assertTrue(error is IOException)
        assertEquals("Trivia service returned an invalid question.", error?.message)
    }

    private class FakeTriviaApi(
        private val response: TriviaResponse,
        private val failure: HttpException? = null
    ) : TriviaApiService {
        var request: List<Int> = emptyList()
            private set

        override suspend fun getQuestions(
            amount: Int,
            category: Int,
            type: String,
            encoding: String
        ): TriviaResponse {
            request = listOf(amount, category)
            assertEquals("multiple", type)
            assertEquals("url3986", encoding)
            failure?.let { throw it }
            return response
        }
    }
}
