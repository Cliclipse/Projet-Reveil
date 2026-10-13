package com.example.projetreveil.ui.theme

import android.app.Activity
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

// Base de la palette
private val AlabasterGrey = Color(0xFFE8E8E8)
private val Silver = Color(0xFFBFBFBF)
private val RosyGranite = Color(0xFF96939B)
private val VintageGrape = Color(0xFF564256)
private val CoralGlow = Color(0xFFFC814A)

// Compléments pour un peu de peps
private val LightText = Color(0xFF302832)
private val LightSurface = Color(0xFFFFFFFF)
private val LightContainer = Color(0xFFF2EAF2)

// Pour dark theme
private val DarkBackground = Color(0xFF211B22)
private val DarkSurface = Color(0xFF302832)
private val DarkPrimary = Color(0xFFD8C5DA)
private val DarkSecondary = Color(0xFFC4BBC8)
private val DarkCoral = Color(0xFFFFA477)
private val DarkText = Color(0xFFF1EAF2)
private val DarkOutline = Color(0xFF655765)
private val DarkContainer = Color(0xFF493849)


private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color(0xFF38253A),

    primaryContainer = DarkContainer,
    onPrimaryContainer = Color(0xFFF1DDF2),

    secondary = DarkSecondary,
    onSecondary = Color(0xFF332B36),

    secondaryContainer = Color(0xFF493F4B),
    onSecondaryContainer = Color(0xFFE9DDEB),

    tertiary = DarkCoral,
    onTertiary = Color(0xFF502000),

    tertiaryContainer = Color(0xFF713B22),
    onTertiaryContainer = Color(0xFFFFDBCA),

    background = DarkBackground,
    onBackground = DarkText,

    surface = DarkSurface,
    onSurface = DarkText,

    surfaceVariant = DarkContainer,
    onSurfaceVariant = Color(0xFFD0C4D2),

    outline = DarkOutline,
    outlineVariant = Color(0xFF493F4B),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = VintageGrape,
    onPrimary = Color.White,

    primaryContainer = LightContainer,
    onPrimaryContainer = VintageGrape,

    secondary = RosyGranite,
    onSecondary = Color.White,

    secondaryContainer = AlabasterGrey,
    onSecondaryContainer = LightText,

    tertiary = CoralGlow,
    onTertiary = Color(0xFF351300),

    tertiaryContainer = Color(0xFFFFDBCA),
    onTertiaryContainer = Color(0xFF542000),

    background = AlabasterGrey,
    onBackground = LightText,

    surface = LightSurface,
    onSurface = LightText,

    surfaceVariant = AlabasterGrey,
    onSurfaceVariant = VintageGrape,

    outline = Silver,
    outlineVariant = Color(0xFFD5D1D6),

    error = Color(0xFFBA1A1A),
    onError = Color.White

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ProjetReveilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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