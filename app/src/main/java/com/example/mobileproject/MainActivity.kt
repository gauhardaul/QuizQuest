package com.example.mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileproject.data.local.QuizDatabase
import com.example.mobileproject.data.repository.HistoryRepository
import com.example.mobileproject.ui.history.HistoryViewModel
import com.example.mobileproject.ui.quiz.QuizViewModel
import com.example.mobileproject.ui.theme.MobileProjectTheme
import com.example.mobileproject.ui.navigation.QuizNavHost
import com.example.mobileproject.ui.settings.SettingsViewModel
import com.example.mobileproject.ui.settings.SettingsViewModelFactory

class MainActivity : ComponentActivity() {
    private val historyRepository by lazy {
        HistoryRepository(QuizDatabase.getDatabase(applicationContext).quizResultDao())
    }

    private val quizViewModel by viewModels<QuizViewModel> {
        viewModelFactory { initializer { QuizViewModel(saveResult = historyRepository::save) } }
    }
    private val historyViewModel by viewModels<HistoryViewModel> {
        viewModelFactory { initializer { HistoryViewModel(historyRepository) } }
    }

    private val settingsViewModel by viewModels<SettingsViewModel> { SettingsViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val savedTheme by settingsViewModel.darkTheme.collectAsState()
            val darkTheme = savedTheme ?: isSystemInDarkTheme()

            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }

            val snackbarHost = remember { SnackbarHostState() }
            LaunchedEffect(settingsViewModel) {
                settingsViewModel.saveErrors.collect {
                    snackbarHost.showSnackbar("Could not save the theme preference.")
                }
            }

            MobileProjectTheme(darkTheme = darkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHost) }
                ) { innerPadding ->
                    QuizNavHost(
                        modifier = Modifier.padding(innerPadding),
                        quizViewModel = quizViewModel,
                        historyViewModel = historyViewModel,
                        darkTheme = darkTheme,
                        onDarkThemeChange = settingsViewModel::setDarkTheme
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileProjectTheme {
        Greeting("Android")
    }
}
