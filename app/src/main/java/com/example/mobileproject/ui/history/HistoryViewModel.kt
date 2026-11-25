package com.example.mobileproject.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.local.QuizResultEntity
import com.example.mobileproject.data.repository.HistoryRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val history: StateFlow<List<QuizResultEntity>?> = repository.history
        .catch {
            _error.value = "Could not load history."
            emit(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updateNote(id: Long, note: String) {
        changeHistory("Could not save the note.") { repository.updateNote(id, note) }
    }

    fun delete(id: Long) {
        changeHistory("Could not delete the attempt.") { repository.delete(id) }
    }

    private fun changeHistory(message: String, action: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                action()
                _error.value = null
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _error.value = message
            }
        }
    }
}
