package com.example.mobileproject.ui.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileproject.ui.theme.MobileProjectTheme

@Composable
fun ResultsScreen(
    topic: String,
    score: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Quiz Finished!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Topic: $topic",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    MobileProjectTheme {
        ResultsScreen(topic = "Math", score = 2, total = 3)
    }
}