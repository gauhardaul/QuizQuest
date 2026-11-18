package com.example.mobileproject

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class TopicsMenuTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun opensTheQuizForEachTopicAndKeepsHistoryAccessible() {
        compose.onNodeWithText("Choose a topic").assertExists()
        compose.onAllNodesWithText("3 questions", substring = true).assertCountEquals(3)

        for (topic in listOf("Math", "IT", "Science")) {
            compose.onNodeWithText(topic, substring = true).performClick()
            compose.onNodeWithText("$topic Quiz").assertExists()
            compose.onNodeWithText("Back").performClick()
        }

        compose.onNodeWithText("History").performClick()
        compose.onNodeWithText("No results yet").assertExists()
        compose.onNodeWithText("Back").performClick()
        compose.onNodeWithText("Choose a topic").assertExists()
    }
}
