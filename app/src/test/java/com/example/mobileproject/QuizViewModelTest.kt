package com.example.mobileproject

import com.example.mobileproject.data.QuizResult
import com.example.mobileproject.data.questionsFor
import com.example.mobileproject.ui.quiz.QuizLoadState
import com.example.mobileproject.ui.quiz.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun retriesTheSameTopicAfterErrorAndEmptyResponses() = runTest {
        val calls = mutableListOf<String>()
        val quiz = QuizViewModel(loadQuestions = { topic ->
            calls += topic
            when (calls.size) {
                1 -> throw IOException("Offline")
                2 -> emptyList()
                else -> questionsFor(topic)
            }
        }, saveResult = {})

        quiz.startQuiz("IT")
        assertEquals(QuizLoadState.Loading, quiz.uiState.value.loadState)
        runCurrent()
        assertEquals(QuizLoadState.Error, quiz.uiState.value.loadState)

        quiz.retry()
        runCurrent()
        assertEquals(QuizLoadState.Empty, quiz.uiState.value.loadState)

        quiz.retry()
        runCurrent()
        assertEquals(QuizLoadState.Success, quiz.uiState.value.loadState)
        assertEquals(listOf("IT", "IT", "IT"), calls)
        assertEquals(3, quiz.uiState.value.questions.size)
    }

    @Test
    fun changingTopicCancelsTheOldRequest() = runTest {
        var wasCancelled = false
        val quiz = QuizViewModel(loadQuestions = { topic ->
            if (topic == "Math") {
                try {
                    awaitCancellation()
                } finally {
                    wasCancelled = true
                }
            }
            questionsFor(topic)
        }, saveResult = {})

        quiz.startQuiz("Math")
        runCurrent()
        quiz.startQuiz("Science")
        runCurrent()

        assertTrue(wasCancelled)
        assertEquals("Science", quiz.uiState.value.topic)
        assertEquals(QuizLoadState.Success, quiz.uiState.value.loadState)
    }

    @Test
    fun publishesTheScoreOnceAndResetsOnlyTheCurrentAttempt() = runTest {
        val saved = mutableListOf<QuizResult>()
        val quiz = QuizViewModel(loadQuestions = { questionsFor(it) }, saveResult = { saved += it })
        quiz.startQuiz("Math")
        runCurrent()
        assertFalse(quiz.nextQuestion())
        quiz.selectAnswer("4")
        assertFalse(quiz.nextQuestion())
        quiz.selectAnswer("21")
        assertFalse(quiz.nextQuestion())
        quiz.selectAnswer("5")
        assertTrue(quiz.nextQuestion())
        assertFalse(quiz.nextQuestion())

        runCurrent()
        val result = QuizResult("Math", 2, 3)
        assertEquals(result, quiz.completedResult.value)
        assertEquals(listOf(result), saved)

        quiz.startQuiz("Math")
        runCurrent()
        assertNull(quiz.completedResult.value)
        assertEquals(0, quiz.uiState.value.score)
        assertEquals(0, quiz.uiState.value.questionIndex)
        assertNull(quiz.uiState.value.selectedAnswer)
        assertEquals(listOf(result), saved)
    }
}
