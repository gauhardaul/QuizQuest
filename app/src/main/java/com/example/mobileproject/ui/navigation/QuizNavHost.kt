
package com.example.mobileproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileproject.ui.history.HistoryScreen
import com.example.mobileproject.ui.history.HistoryViewModel
import com.example.mobileproject.ui.quiz.QuizScreen
import com.example.mobileproject.ui.quiz.QuizViewModel
import com.example.mobileproject.ui.results.ResultsScreen
import com.example.mobileproject.ui.topics.TopicsScreen

@Composable
fun QuizNavHost(
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel,
    historyViewModel: HistoryViewModel,
    darkTheme: Boolean = false,
    onDarkThemeChange: ((Boolean) -> Unit)? = null
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "topics",
        modifier = modifier
    ) {


        composable("topics") {
            TopicsScreen(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
                onTopicClick = { topic ->
                    quizViewModel.startQuiz(topic)
                    navController.navigate("quiz/$topic")
                },
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }


        composable("quiz/{topicId}") { entry ->

            val topicId =
                entry.arguments?.getString("topicId") ?: "Math"

            LaunchedEffect(topicId) {
                if (quizViewModel.uiState.value.topic != topicId) {
                    quizViewModel.startQuiz(topicId)
                }
            }

            QuizScreen(
                viewModel = quizViewModel,
                onFinished = {
                    navController.navigate("results") {
                        popUpTo("topics")
                    }
                },
                onBack = {
                    quizViewModel.cancelLoading()
                    navController.navigateUp()
                }
            )
        }

        composable("results") {
            val completedResult by quizViewModel.completedResult.collectAsState()
            val saveFailed by quizViewModel.saveFailed.collectAsState()
            val result = completedResult
            if (result == null) {
                LaunchedEffect(Unit) {
                    if (navController.currentDestination?.route == "results") {
                        navController.popBackStack("topics", false)
                    }
                }
                return@composable
            }

            ResultsScreen(
                topic = result.topic,
                score = result.score,
                total = result.total,
                saveFailed = saveFailed,
                onRetryClick = {
                    quizViewModel.startQuiz(result.topic)
                    navController.navigate("quiz/${result.topic}") {
                        popUpTo("topics")
                    }
                },
                onBackToTopicsClick = {
                    navController.popBackStack("topics", false)
                }
            )
        }

        composable("history") {
            val history by historyViewModel.history.collectAsState()
            val error by historyViewModel.error.collectAsState()
            HistoryScreen(
                history = history,
                onBack = { navController.navigateUp() },
                onSaveNote = historyViewModel::updateNote,
                onDelete = historyViewModel::delete,
                error = error
            )
        }
    }
}
