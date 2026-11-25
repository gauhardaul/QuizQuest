package com.example.mobileproject.data.repository

import com.example.mobileproject.data.QuizResult
import com.example.mobileproject.data.local.QuizResultDao
import com.example.mobileproject.data.local.QuizResultEntity

class HistoryRepository(private val dao: QuizResultDao) {
    val history = dao.observeResults()

    suspend fun save(result: QuizResult) {
        dao.insert(
            QuizResultEntity(topic = result.topic, score = result.score, total = result.total)
        )
    }

    suspend fun updateNote(id: Long, note: String) {
        dao.updateNote(id, note.trim())
    }

    suspend fun delete(id: Long) {
        dao.delete(id)
    }
}
