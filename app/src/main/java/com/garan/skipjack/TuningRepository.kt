/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private val audioSource: AudioSource,
) {
    private var tuningConfig = TuningConfig.WHOLE_NOTES

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
