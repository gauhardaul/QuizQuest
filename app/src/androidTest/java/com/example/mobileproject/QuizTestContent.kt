package com.example.mobileproject

import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.example.mobileproject.data.questionsFor
import com.example.mobileproject.ui.navigation.QuizNavHost
import com.example.mobileproject.ui.quiz.QuizViewModel
import com.example.mobileproject.ui.theme.MobileProjectTheme

internal fun ComposeContentTestRule.setQuizTestContent() {
    setContent {
        val quizViewModel = remember { QuizViewModel(loadQuestions = { questionsFor(it) }) }
        MobileProjectTheme {
            QuizNavHost(quizViewModel = quizViewModel)
        }
    }
}
