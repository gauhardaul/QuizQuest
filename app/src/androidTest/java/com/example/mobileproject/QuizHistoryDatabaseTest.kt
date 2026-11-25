package com.example.mobileproject

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mobileproject.data.QuizResult
import com.example.mobileproject.data.local.QuizDatabase
import com.example.mobileproject.data.repository.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class QuizHistoryDatabaseTest {
    @Test
    fun addsEditsAndDeletesAttemptsAcrossDatabaseReopens() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val databaseName = "quiz-test-${UUID.randomUUID()}.db"
        suspend fun withDatabase(test: suspend (QuizDatabase) -> Unit) {
            val database = Room.databaseBuilder(
                context, QuizDatabase::class.java, databaseName
            ).build()
            try {
                test(database)
            } finally {
                database.close()
            }
        }

        try {
            withTimeout(15_000) {
                withDatabase { database ->
                    val repository = HistoryRepository(database.quizResultDao())
                    assertEquals(0, repository.history.first().size)
                    repository.save(QuizResult("Math", 2, 3))
                    repository.save(QuizResult("Science", 1, 3))

                    val saved = repository.history.first { it.size == 2 }
                    assertEquals(listOf("Science", "Math"), saved.map { it.topic })
                    repository.updateNote(saved.first().id, " Review planets ")
                    assertEquals("Review planets", repository.history.first().first().note)
                    repository.delete(saved.last().id)
                }

                withDatabase { database ->
                    val remaining = HistoryRepository(database.quizResultDao()).history.first().single()
                    assertEquals("Science", remaining.topic)
                    assertEquals(1, remaining.score)
                    assertEquals("Review planets", remaining.note)
                }
            }
        } finally {
            context.deleteDatabase(databaseName)
        }
        Unit
    }
}
