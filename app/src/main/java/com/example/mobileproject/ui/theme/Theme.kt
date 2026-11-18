package com.example.mobileproject.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = QuizPurple,
    onPrimary = Color.White,
    primaryContainer = QuizPurpleLight,
    onPrimaryContainer = QuizPurpleDeep,
    secondary = QuizTeal,
    onSecondary = Color.White,
    tertiary = QuizOrange,
    onTertiary = Color.White,
    background = QuizBackground,
    onBackground = QuizText,
    surface = QuizBackground,
    onSurface = QuizText
)

private val DarkColorScheme = darkColorScheme(
    primary = QuizPurpleDark,
    onPrimary = QuizPurpleDeep,
    primaryContainer = QuizPurpleDarkContainer,
    onPrimaryContainer = Color.White,
    secondary = QuizTealDark,
    tertiary = QuizOrangeDark,
    background = QuizBackgroundDark,
    onBackground = QuizTextDark,
    surface = QuizBackgroundDark,
    onSurface = QuizTextDark
)

@Composable
fun MobileProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}