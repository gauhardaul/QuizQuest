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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileproject.ui.theme.MobileProjectTheme
import com.example.mobileproject.ui.navigation.QuizNavHost
import com.example.mobileproject.ui.settings.SettingsViewModel
import com.example.mobileproject.ui.settings.SettingsViewModelFactory

class MainActivity : ComponentActivity() {
    private val settingsViewModel by viewModels<SettingsViewModel> { SettingsViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val savedTheme by settingsViewModel.darkTheme.collectAsState()
            val darkTheme = savedTheme ?: isSystemInDarkTheme()

            MobileProjectTheme(darkTheme = darkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizNavHost(
                        modifier = Modifier.padding(innerPadding),
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
