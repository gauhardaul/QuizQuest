package com.example.mobileproject.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileproject.ui.theme.MobileProjectTheme

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onFinished: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val question = state.currentQuestion

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "${state.topic} Quiz",
            style = MaterialTheme.typography.headlineSmall
        )

        if (state.loadState == QuizLoadState.Loading) {
            CircularProgressIndicator()
            Text("Loading questions…")
        } else if (question != null) {
            Text("Question ${state.questionIndex + 1} of ${state.questions.size}")
            Text(question.text)

            question.options.forEach { answer ->
                OutlinedButton(
                    onClick = { viewModel.selectAnswer(answer) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val label = if (answer == state.selectedAnswer) "✓ $answer" else answer
                    Text(label)
                }
            }

            Button(
                onClick = {
                    if (viewModel.nextQuestion()) {
                        onFinished()
                    }
                },
                enabled = state.selectedAnswer != null
            ) {
                val isLast = state.questionIndex == state.questions.lastIndex
                Text(if (isLast) "Finish Quiz" else "Next Question")
            }
        } else if (state.loadState == QuizLoadState.Error) {
            Text("Could not load questions.")
            Button(onClick = viewModel::retry) {
                Text("Retry")
            }
        } else if (state.questions.isEmpty()) {
            Text("No questions available")
        }

        OutlinedButton(onClick = onBack) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizScreenPreview() {
    val previewViewModel = remember { QuizViewModel().apply { startQuiz("Math") } }

    MobileProjectTheme {
        QuizScreen(
            viewModel = previewViewModel,
            onFinished = {},
            onBack = {}
        )
    }
}
