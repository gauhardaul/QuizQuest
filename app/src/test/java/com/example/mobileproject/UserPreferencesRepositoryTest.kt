package com.example.mobileproject

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.mobileproject.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class UserPreferencesRepositoryTest {
    @get:Rule
    val directory = TemporaryFolder()

    @Test
    fun savedDarkAndLightThemesSurviveReopeningTheFile() = runBlocking {
        val file = File(directory.root, "settings.preferences_pb")

        withRepository(file) { repository ->
            assertNull(repository.darkTheme.first())
            repository.saveDarkTheme(true)
        }

        withRepository(file) { repository ->
            assertEquals(true, repository.darkTheme.first())
            repository.saveDarkTheme(false)
        }

        withRepository(file) { repository ->
            assertEquals(false, repository.darkTheme.first())
        }
    }

    private suspend fun withRepository(
        file: File,
        useRepository: suspend (UserPreferencesRepository) -> Unit
    ) {
        val job = Job()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + job),
            produceFile = { file }
        )

        try {
            useRepository(UserPreferencesRepository(dataStore))
        } finally {
            job.cancelAndJoin()
        }
    }
}
