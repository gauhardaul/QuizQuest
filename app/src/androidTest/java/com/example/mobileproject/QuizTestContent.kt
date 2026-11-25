package com.example.mobileproject

import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.example.mobileproject.data.local.QuizResultDao
import com.example.mobileproject.data.local.QuizResultEntity
import com.example.mobileproject.data.questionsFor
import com.example.mobileproject.data.repository.HistoryRepository
import com.example.mobileproject.ui.history.HistoryViewModel
import com.example.mobileproject.ui.navigation.QuizNavHost
import com.example.mobileproject.ui.quiz.QuizViewModel
import com.example.mobileproject.ui.theme.MobileProjectTheme
import kotlinx.coroutines.flow.MutableStateFlow

internal fun ComposeContentTestRule.setQuizTestContent() {
    setContent {
        val repository = remember { HistoryRepository(TestHistoryDao()) }
        val quizViewModel = remember {
            QuizViewModel(loadQuestions = { questionsFor(it) }, saveResult = repository::save)
        }
        val historyViewModel = remember { HistoryViewModel(repository) }

        MobileProjectTheme {
            QuizNavHost(quizViewModel = quizViewModel, historyViewModel = historyViewModel)
        }
    }
}

private class TestHistoryDao : QuizResultDao {
    private val results = MutableStateFlow<List<QuizResultEntity>>(emptyList())
    private var nextId = 1L

    override suspend fun insert(result: QuizResultEntity) {
        results.value = listOf(result.copy(id = nextId++)) + results.value
    }

    override fun observeResults() = results

    override suspend fun updateNote(id: Long, note: String) {
        results.value = results.value.map { if (it.id == id) it.copy(note = note) else it }
    }

    override suspend fun delete(id: Long) {
        results.value = results.value.filterNot { it.id == id }
    }
}
