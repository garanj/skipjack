package com.garan.skipjack.audio

import be.tarsos.dsp.pitch.PitchDetectionResult
import kotlinx.coroutines.flow.Flow

/**
 * A source of audio that provides a flow of [PitchDetectionResult].
 */
interface AudioSource {
    val pitchFlow: Flow<PitchDetectionResult>
}