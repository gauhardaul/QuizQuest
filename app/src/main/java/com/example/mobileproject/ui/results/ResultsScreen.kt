package com.example.mobileproject.ui.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileproject.ui.components.QuizButton
import com.example.mobileproject.ui.theme.MobileProjectTheme

@Composable
fun ResultsScreen(
    topic: String,
    score: Int,
    total: Int,
    onRetryClick: () -> Unit,
    onBackToTopicsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percent = calculatePercent(score, total)
    val message = getResultMessage(percent)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Finished!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Topic: $topic",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "$percent% correct answers",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        QuizButton(
            text = "Try Again",
            onClick = onRetryClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuizButton(
            text = "Back to Topics",
            onClick = onBackToTopicsClick,
            isSecondary = true
        )
    }
}

@Preview(showBackground = true, name = "2 из 3")
@Composable
fun ResultsScreenPreview() {
    MobileProjectTheme {
        ResultsScreen(
            topic = "Math",
            score = 2,
            total = 3,
            onRetryClick = {},
            onBackToTopicsClick = {}
        )
    }
}


@Preview(showBackground = true, name = "0 из 3")
@Composable
fun ResultsScreenZeroPreview() {
    MobileProjectTheme {
        ResultsScreen(
            topic = "IT",
            score = 0,
            total = 3,
            onRetryClick = {},
            onBackToTopicsClick = {}
        )
    }
}

@Preview(showBackground = true, name = "3 из 3")
@Composable
fun ResultsScreenFullPreview() {
    MobileProjectTheme {
        ResultsScreen(
            topic = "Science",
            score = 3,
            total = 3,
            onRetryClick = {},
            onBackToTopicsClick = {}
        )
    }
}