
package com.example.mobileproject.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileproject.ui.quiz.QuizScreen
import com.example.mobileproject.ui.quiz.QuizViewModel
import com.example.mobileproject.ui.topics.TopicsScreen

@Composable
fun QuizNavHost(
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel = viewModel()
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "topics",
        modifier = modifier
    ) {


        composable("topics") {
            TopicsScreen(
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
                onBack = { navController.navigateUp() }
            )
        }

        composable("results") {
            val state by quizViewModel.uiState.collectAsState()
            Column {
                Text("Results Screen")
                Text("${state.score} / ${state.questions.size}")

                Button(onClick = {
                    navController.navigateUp()
                }) {
                    Text("Back")
                }

                Button(onClick = {
                    navController.popBackStack("topics", false)
                }) {
                    Text("Back to Topics")
                }

            }
        }

        composable("history") {
            Column {
                Text("History Screen")

                Button(onClick = {
                    navController.navigateUp()
                }) {
                    Text("Back")
                }
            }
        }
    }
}
