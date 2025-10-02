package com.garan.skipjack

import be.tarsos.dsp.pitch.PitchDetectionResult
import com.garan.skipjack.audio.AudioSource
import kotlinx.coroutines.flow.channelFlow

data class FakeAudioSource(val pitchList: List<Float>) : AudioSource {
    override val pitchFlow = channelFlow {
        pitchList.forEach { pitch ->
            val result = PitchDetectionResult()
            result.pitch = pitch
            result.probability = 1f
            result.isPitched = true
            trySend(result)
        }
    }
}
