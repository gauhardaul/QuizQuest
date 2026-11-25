package com.example.mobileproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results ORDER BY id DESC")
    fun observeResults(): Flow<List<QuizResultEntity>>

    @Query("UPDATE quiz_results SET note = :note WHERE id = :id")
    suspend fun updateNote(id: Long, note: String)

    @Query("DELETE FROM quiz_results WHERE id = :id")
    suspend fun delete(id: Long)
}
