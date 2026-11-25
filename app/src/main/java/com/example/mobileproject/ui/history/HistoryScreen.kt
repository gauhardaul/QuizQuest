package com.example.mobileproject.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileproject.data.local.QuizResultEntity
import com.example.mobileproject.ui.components.QuizButton

@Composable
fun HistoryScreen(
    history: List<QuizResultEntity>?,
    onBack: () -> Unit,
    onSaveNote: (Long, String) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
) {
    var editingId by rememberSaveable { mutableStateOf<Long?>(null) }
    var deletingId by rememberSaveable { mutableStateOf<Long?>(null) }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("History", style = MaterialTheme.typography.headlineLarge)
        if (error != null) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (history == null) {
                item { CircularProgressIndicator() }
            } else if (history.isEmpty() && error == null) {
                item { Text("No results yet") }
            }

            items(history.orEmpty(), key = { it.id }) { result ->
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(result.topic, style = MaterialTheme.typography.titleMedium)
                        Text("${result.score}/${result.total}")
                    }
                    if (result.note.isNotBlank()) {
                        Text(result.note)
                    }
                    Row {
                        TextButton(onClick = { editingId = result.id }) { Text("Edit note") }
                        TextButton(onClick = { deletingId = result.id }) { Text("Delete") }
                    }
                }
            }
        }

        QuizButton(text = "Back", onClick = onBack, isSecondary = true)
    }

    history?.find { it.id == editingId }?.let { result ->
        EditNoteDialog(
            initialNote = result.note,
            onSave = { note ->
                onSaveNote(result.id, note)
                editingId = null
            },
            onDismiss = { editingId = null }
        )
    }

    history?.find { it.id == deletingId }?.let { result ->
        DeleteAttemptDialog(
            topic = result.topic,
            onDelete = {
                onDelete(result.id)
                deletingId = null
            },
            onDismiss = { deletingId = null }
        )
    }
}
