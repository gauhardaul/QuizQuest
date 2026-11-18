package com.example.mobileproject.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

        if (question != null) {
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
        } else if (state.questions.isEmpty()) {
            Text("No questions available")
        }

        OutlinedButton(onClick = onBack) {
            Text("Back")
        }
    }
}
