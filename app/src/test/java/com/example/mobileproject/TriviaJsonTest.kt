package com.example.mobileproject

import com.example.mobileproject.data.remote.TriviaQuestionDto
import com.example.mobileproject.data.remote.TriviaResponse
import com.example.mobileproject.data.remote.triviaJson
import org.junit.Assert.assertEquals
import org.junit.Test

class TriviaJsonTest {
    @Test
    fun parsesAnApiResponseWithAdditionalFields() {
        val response = triviaJson.decodeFromString<TriviaResponse>(
            """
            {
                "response_code": 0,
                "results": [{
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Science%3A%20Mathematics",
                    "question": "What%20is%202%20%2B%202%3F",
                    "correct_answer": "4",
                    "incorrect_answers": ["3", "5", "6"]
                }]
            }
            """.trimIndent()
        )

        assertEquals(0, response.responseCode)
        assertEquals(
            TriviaQuestionDto("What%20is%202%20%2B%202%3F", "4", listOf("3", "5", "6")),
            response.results.single()
        )
    }
}
