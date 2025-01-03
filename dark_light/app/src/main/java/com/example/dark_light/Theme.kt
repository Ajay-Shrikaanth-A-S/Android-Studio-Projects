package com.example.Theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

// Additional color schemes
private val Gradient1Colors = lightColorScheme(
    primary = Color(0xFFB71C1C), // Dark Red
    secondary = Color(0xFFD32F2F), // Red
    background = Color(0xFFFFEBEE), // Light Pink
    surface = Color(0xFFFFCDD2), // Light Red
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val Gradient2Colors = lightColorScheme(
    primary = Color(0xFF0288D1), // Blue
    secondary = Color(0xFF03A9F4), // Light Blue
    background = Color(0xFFE1F5FE), // Light Cyan
    surface = Color(0xFFB3E5FC), // Lighter Blue
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

// Use the default Typography from Material3
private val AppTypography = Typography()

@Composable
fun DarkLightTheme(
    themeIndex: Int = 0,
    content: @Composable () -> Unit
) {
    val colors = when (themeIndex) {
        1 -> Gradient1Colors
        2 -> Gradient2Colors
        else -> if (themeIndex == 0) LightColors else DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
