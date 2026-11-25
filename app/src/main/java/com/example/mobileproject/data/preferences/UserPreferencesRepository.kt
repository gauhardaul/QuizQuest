package com.example.mobileproject.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

internal val Context.quizSettings by preferencesDataStore(name = "quiz_settings")

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme")

    val darkTheme: Flow<Boolean?> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferences -> preferences[darkThemeKey] }
}
