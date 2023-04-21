package com.garan.skipjack.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

internal val wearColorPalette: Colors = Colors(
    primary = Color(0xF6, 0xD7, 0x0B),
    primaryVariant = Color.LightGray,
    error = Color.Red,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)

@Composable
fun SkipjackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = wearColorPalette,
        content = content
    )
}
