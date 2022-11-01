package com.azad.note_app_clean_arc_mvvm_jetpack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Gray,
    background = Color.DarkGray,
    onBackground = Color.LightGray,
    surface = Color.White,
    onSurface = Color.LightGray,
    secondary = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color.Gray,
    background = Color.DarkGray,
    onBackground = Color.LightGray,
    surface = Color.White,
    onSurface = Color.LightGray,
    secondary = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Note_App_Clean_Arc_MVVM_JetpackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}