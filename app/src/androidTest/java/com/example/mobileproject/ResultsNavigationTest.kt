package com.example.mobileproject

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ResultsNavigationTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun showsResultsRestartsSameTopicAndReturnsToTopics() {
        compose.setQuizTestContent()

        compose.onNodeWithText("Math").performClick()
        answer("4", "Next Question")
        answer("21", "Next Question")
        answer("5", "Finish Quiz")

        compose.onNodeWithText("Quiz Finished!").assertExists()
        compose.onNodeWithText("Topic: Math").assertExists()
        compose.onNodeWithText("2 / 3").assertExists()
        compose.onNodeWithText("67% correct answers").assertExists()

        compose.onNodeWithText("Try Again").performClick()
        compose.onNodeWithText("Math Quiz").assertExists()
        compose.onNodeWithText("Question 1 of 3").assertExists()
        compose.onNodeWithText("Next Question").assertIsNotEnabled()
        answer("3", "Next Question")
        answer("18", "Next Question")
        answer("5", "Finish Quiz")

        compose.onNodeWithText("0 / 3").assertExists()
        compose.onNodeWithText("0% correct answers").assertExists()
        compose.onNodeWithText("Back to Topics").performClick()
        compose.onNodeWithText("QuizQuest").assertExists()
        compose.onNodeWithText("Math").assertExists()
    }

    private fun answer(option: String, action: String) {
        compose.onNodeWithText(option).performClick()
        compose.onNodeWithText(action).performClick()
    }
}
