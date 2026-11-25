package com.example.mobileproject

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class HistoryNavigationTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun showsEmptyHistoryThenRecordsAttemptsForEveryTopic() {
        compose.setQuizTestContent()

        compose.onNodeWithText("History").performClick()
        compose.onNodeWithText("No results yet").assertExists()
        compose.onNodeWithText("Back").performClick()

        finishQuiz("Math", "4", "21", "6")
        finishQuiz("IT", "2", "Keyboard", "Kotlin")
        finishQuiz("Science", "Earth", "Oxygen", "CO2")

        compose.onNodeWithText("History").performClick()
        compose.onNodeWithText("No results yet").assertDoesNotExist()
        compose.onNodeWithText("Math").assertExists()
        compose.onNodeWithText("3/3").assertExists()
        compose.onNodeWithText("IT").assertExists()
        compose.onNodeWithText("2/3").assertExists()
        compose.onNodeWithText("Science").assertExists()
        compose.onNodeWithText("0/3").assertExists()

        compose.onNodeWithText("Back").performClick()
        compose.onNodeWithText("QuizQuest").assertExists()
    }

    private fun finishQuiz(topic: String, first: String, second: String, third: String) {
        compose.onNodeWithText(topic).performClick()
        compose.onNodeWithText(first).performClick()
        compose.onNodeWithText("Next Question").performClick()
        compose.onNodeWithText(second).performClick()
        compose.onNodeWithText("Next Question").performClick()
        compose.onNodeWithText(third).performClick()
        compose.onNodeWithText("Finish Quiz").performClick()
        compose.onNodeWithText("Back to Topics").performClick()
    }
}
