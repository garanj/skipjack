package com.garan.skipjack

import com.garan.skipjack.audio.AudioSource
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.model.TunedStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.math.absoluteValue


class TuningRepository @Inject constructor(
    private val audioSource: AudioSource
) {
    private var tuningConfig = TuningConfig.ALL_NOTES

    val tuningStatusFlow = flow<TunedStatus> {
        if (tuningConfig != TuningConfig.NONE) {
            audioSource.pitchFlow.collect { pitchDetection ->
                if (pitchDetection.isPitched && pitchDetection.probability >= PITCH_PROBABILITY_MIN) {
                    val nearestNote = tuningConfig
                        .notes.minBy { it.nearestPitchDifference(pitchDetection.pitch).absoluteValue }

                    val difference = nearestNote.nearestPitchDifference(pitchDetection.pitch)
                    emit(TunedStatus.TuningInfo(nearestNote.targetNote, difference))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    fun setTuningConfig(config: TuningConfig) {
        tuningConfig = config
    }
}
