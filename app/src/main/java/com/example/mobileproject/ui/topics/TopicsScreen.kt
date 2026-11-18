
package com.example.mobileproject.ui.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileproject.ui.components.QuizButton
import com.example.mobileproject.ui.theme.MobileProjectTheme

@Composable
fun TopicsScreen(
    onTopicClick: (String) -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: TopicsViewModel = viewModel()
) {

    val topics by viewModel.topics.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text("QuizQuest", style = MaterialTheme.typography.headlineLarge)
            Text("Choose a topic", style = MaterialTheme.typography.bodyLarge)
        }

        items(topics) { topic ->
            Button(onClick = { onTopicClick(topic) }) {
                Text(topic)
            }
        }

        item {
            QuizButton(
                text = "History",
                onClick = onHistoryClick,
                isSecondary = true
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopicsScreenPreview() {
    val previewViewModel = remember { TopicsViewModel() }

    MobileProjectTheme {
        TopicsScreen(
            viewModel = previewViewModel,
            onTopicClick = {},
            onHistoryClick = {}
        )
    }
}
