
package com.example.mobileproject.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileproject.ui.topics.TopicsScreen

@Composable
fun QuizNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "topics"
    ) {


        composable("topics") {
            TopicsScreen(
                onTopicClick = { topic ->
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

            Column {
                Text(text = "Quiz: $topicId")

                Button(onClick = {
                    navController.navigate("results")
                }) {
                    Text("Finish Quiz")
                }

                Button(onClick = {
                    navController.navigateUp()
                }) {
                    Text("Back")
                }
            }
        }

        composable("results") {
            Column {
                Text("Results Screen")

                Button(onClick = {
                    navController.navigateUp()
                }) {
                    Text("Back")
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
