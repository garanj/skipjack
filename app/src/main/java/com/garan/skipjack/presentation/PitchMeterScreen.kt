package com.garan.skipjack.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.skipjack.TUNING_NOTE_TIMEOUT_MS
import com.garan.skipjack.components.PitchMeter
import com.garan.skipjack.components.PlaceholderPitchMeter
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TunedStatus
import com.garan.skipjack.theme.SkipjackTheme
import kotlinx.coroutines.delay

@Composable
fun PitchMeterScreen(
    status: TunedStatus
) {
    var timedout by remember { mutableStateOf(false) }
    LaunchedEffect(status) {
        timedout = false
        delay(TUNING_NOTE_TIMEOUT_MS)
        timedout = true
    }
    if (!timedout && status is TunedStatus.TuningInfo) {
        PitchMeter(info = status)
    } else {
        PlaceholderPitchMeter()
    }
}

@WearPreviewDevices
@Composable
fun PitchMeterScreenPreview() {
    val info = TunedStatus.TuningInfo(
        note = Note.B,
        pitchDifference = 0.1
    )
    SkipjackTheme {
        PitchMeterScreen(info)
    }
}