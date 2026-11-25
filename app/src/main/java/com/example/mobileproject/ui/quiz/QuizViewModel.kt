package com.example.mobileproject.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.mobileproject.data.Question
import com.example.mobileproject.data.QuizResult
import com.example.mobileproject.data.questionsFor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class QuizLoadState {
    Loading,
    Success,
    Error,
    Empty
}

data class QuizUiState(
    val loadState: QuizLoadState = QuizLoadState.Loading,
    val topic: String = "",
    val questions: List<Question> = emptyList(),
    val questionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val isFinished: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(questionIndex)
}

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val _history = MutableStateFlow<List<QuizResult>>(emptyList())
    val history: StateFlow<List<QuizResult>> = _history.asStateFlow()

    fun startQuiz(topic: String) {
        _uiState.value = QuizUiState(
            loadState = QuizLoadState.Success,
            topic = topic,
            questions = questionsFor(topic)
        )
    }

    fun selectAnswer(answer: String) {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        if (!state.isFinished && answer in question.options) {
            _uiState.value = state.copy(selectedAnswer = answer)
        }
    }

    fun nextQuestion(): Boolean {
        val state = _uiState.value
        val question = state.currentQuestion ?: return false
        val answer = state.selectedAnswer ?: return false
        if (state.isFinished) return false

        val score = if (answer == question.correctAnswer) state.score + 1 else state.score
        val nextIndex = state.questionIndex + 1
        val isFinished = nextIndex == state.questions.size

        _uiState.value = state.copy(
            questionIndex = nextIndex,
            selectedAnswer = null,
            score = score,
            isFinished = isFinished
        )
        if (isFinished) {
            val result = QuizResult(state.topic, score, state.questions.size)
            _history.value = _history.value + result
        }

        return isFinished
    }
}
