package com.garan.skipjack.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme

internal val colorScheme: ColorScheme = ColorScheme(
    primary = Color(0xF6, 0xD7, 0x0B),
    secondary = Color(0x8B, 0xC3, 0x4A, 0xFF),
    surfaceContainer = Color(0x40, 0x40, 0x40, 0xFF),
    error = Color.Red,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black,
    onSurface = Color(0xF6, 0xD7, 0x0B),
)

@Composable
fun SkipjackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
