package com.garan.skipjack

import com.garan.skipjack.audio.AudioSource
import com.garan.skipjack.model.TunedStatus
import com.garan.skipjack.model.TuningConfigurationGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.math.absoluteValue


class TuningRepository @Inject constructor(
    private val audioSource: AudioSource
) {
    private var tuningNoteGroup = TuningConfigurationGroup.EMPTY

    val tuningStatusFlow = flow<TunedStatus> {
        if (tuningNoteGroup != TuningConfigurationGroup.EMPTY) {
            audioSource.pitchFlow.collect { pitchDetection ->
                if (pitchDetection.isPitched && pitchDetection.probability >= PITCH_PROBABILITY_MIN) {
                    val nearestNote = tuningNoteGroup
                        .notes.minBy { it.nearestPitchDifference(pitchDetection.pitch).absoluteValue }

                    val difference = nearestNote.nearestPitchDifference(pitchDetection.pitch)
                    emit(TunedStatus.TuningInfo(nearestNote.targetNote, difference))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    fun setTuningGroup(group: TuningConfigurationGroup) {
        tuningNoteGroup = group
    }
}
