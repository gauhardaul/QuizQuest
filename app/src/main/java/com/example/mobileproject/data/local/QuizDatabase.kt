package com.example.mobileproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuizResultEntity::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var instance: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_history.db"
            ).build().also { instance = it }
        }
    }
}
