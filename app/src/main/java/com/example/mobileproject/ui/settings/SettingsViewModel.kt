package com.example.mobileproject.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class SettingsViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {
    val darkTheme: StateFlow<Boolean?> = repository.darkTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _saveErrors = MutableSharedFlow<Unit>()
    val saveErrors = _saveErrors.asSharedFlow()

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            try {
                repository.saveDarkTheme(enabled)
            } catch (_: IOException) {
                _saveErrors.emit(Unit)
            }
        }
    }
}
