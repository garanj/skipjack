package com.garan.skipjack.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.garan.skipjack.MainActivity
import com.garan.skipjack.NO_TUNED_NOTE_AUTO_EXIT_TIMEOUT_MS
import com.garan.skipjack.R
import com.garan.skipjack.theme.SkipjackTheme
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun PlaceholderPitchMeter() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(NO_TUNED_NOTE_AUTO_EXIT_TIMEOUT_MS)
        (context as MainActivity).finish()
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val brush = Brush.sweepGradient(
            0.5f to Color.Black,
            0.7f to Color.Gray,
            0.8f to Color.Gray,
            1f to Color.Black
        )
        for (i in 15..45) {
            val minuteAngle = i * PI / 30

            val radius = if (i % 5 == 0) {
                center.x - 40f
            } else {
                center.x - 20f
            }

            val endX = this.center.x + center.x * sin(minuteAngle)
            val endY = this.center.y + center.y * cos(minuteAngle)
            val startX = this.center.x + radius * sin(minuteAngle)
            val startY = this.center.y + radius * cos(minuteAngle)
            drawLine(
                brush = brush,
                start = Offset(
                    startX.toFloat(),
                    startY.toFloat()
                ),
                end = Offset(
                    endX.toFloat(),
                    endY.toFloat()
                ),
                strokeWidth = 5f
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.waiting),
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
    }
}

@Preview(
    device = Devices.WEAR_OS_LARGE_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun PlaceholderPitchMeterPreview() {
    SkipjackTheme {
        PlaceholderPitchMeter()
    }
}