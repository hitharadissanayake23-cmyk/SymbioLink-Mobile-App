package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Student 1: Material 3 Color Schemes for SymbioLink
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = VeryLightBlue,
    onPrimaryContainer = DarkBlue,
    secondary = PrimaryGreen,
    onSecondary = Color.White,
    secondaryContainer = VeryLightGreen,
    onSecondaryContainer = PrimaryGreen,
    tertiary = SecondaryBlue,
    onTertiary = Color.White,
    background = AppBackground,
    onBackground = MainText,
    surface = CardBackground,
    onSurface = MainText,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = SecondaryText,
    error = ErrorRed,
    onError = Color.White,
    outline = SubtleBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = SecondaryBlue,
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = LightGreen,
    onSecondary = Color(0xFF003913),
    secondaryContainer = Color(0xFF00531E),
    onSecondaryContainer = Color(0xFF9FF79E),
    background = Color(0xFF121417),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF24272B),
    onSurfaceVariant = Color(0xFFC3C7CF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    outline = Color(0xFF43474E)
)

@Composable
fun SymbioLinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
