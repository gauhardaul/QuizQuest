package com.example.mobileproject.ui.settings

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileproject.data.preferences.UserPreferencesRepository
import com.example.mobileproject.data.preferences.quizSettings

val SettingsViewModelFactory = viewModelFactory {
    initializer {
        val application = checkNotNull(this[APPLICATION_KEY])
        SettingsViewModel(UserPreferencesRepository(application.quizSettings))
    }
}
