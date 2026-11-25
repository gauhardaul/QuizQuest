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
}
