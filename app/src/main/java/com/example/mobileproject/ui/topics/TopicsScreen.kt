
package com.example.mobileproject.ui.topics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TopicsScreen(
    onTopicClick: (String) -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: TopicsViewModel = viewModel()
) {

    val topics by viewModel.topics.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "QuizQuest",
            fontSize = 28.sp
        )

        LazyColumn {
            items(topics) { topic ->
                Button(
                    onClick = {
                        onTopicClick(topic)
                    }
                ) {
                    Text(text = topic)
                }
            }
        }

        Button(onClick = onHistoryClick) {
            Text("History")
        }
    }
}
