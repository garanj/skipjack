package com.garan.skipjack.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TunedStatus
import com.garan.skipjack.theme.SkipjackTheme
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

const val TUNED_BOUNDARY = PI / 32
const val POINTER_WIDTH = 20f

@Composable
fun PitchMeter(info: TunedStatus.TuningInfo) {
    val animatePitch by animateFloatAsState(
        targetValue = info.pitchDifference.toFloat().coerceIn(-2.0f, 2.0f),
        animationSpec = tween(durationMillis = 200),
        label = "",
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val brush = Brush.sweepGradient(
            0.5f to Color.Black,
            0.7f to info.note.color,
            0.8f to info.note.color,
            1f to Color.Black,
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
                    startY.toFloat(),
                ),
                end = Offset(
                    endX.toFloat(),
                    endY.toFloat(),
                ),
                strokeWidth = 5f,
            )
        }
        val angle = animatePitch * PI / 3
        val drawAngle = angle - PI / 2
        val startX = this.center.x + 50 * cos(drawAngle)
        val startY = this.center.y + 50 * sin(drawAngle)
        val xEnd = center.x + cos(drawAngle) * (center.x - POINTER_WIDTH)
        val yEnd = center.y + sin(drawAngle) * (center.y - POINTER_WIDTH)
        drawLine(
            color = info.note.color,
            start = Offset(startX.toFloat(), startY.toFloat()),
            end = Offset(xEnd.toFloat(), yEnd.toFloat()),
            strokeWidth = POINTER_WIDTH,
            cap = StrokeCap.Round,
        )
        if (angle.absoluteValue > TUNED_BOUNDARY) {
            drawLine(
                color = Color.Black,
                start = Offset(startX.toFloat(), startY.toFloat()),
                end = Offset(xEnd.toFloat(), yEnd.toFloat()),
                strokeWidth = POINTER_WIDTH - 6,
                cap = StrokeCap.Round,
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val superscript = SpanStyle(
            baselineShift = BaselineShift(0.3f),
            fontSize = 24.sp,
        )
        val label = buildAnnotatedString {
            append(info.note.name.first())
            if (info.note.isSharp) {
                withStyle(superscript) {
                    append("♯")
                }
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.displayLarge,
            color = info.note.color,
        )
    }
}

@WearPreviewDevices
@Composable
fun PitchMeterPreview() {
    val info = TunedStatus.TuningInfo(
        note = Note.A_SHARP,
        pitchDifference = 0.5,
    )
    SkipjackTheme {
        PitchMeter(info)
    }
}
