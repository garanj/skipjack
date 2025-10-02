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
package com.garan.skipjack.model

import com.garan.skipjack.A_ZERO_FREQ
import com.garan.skipjack.NOTE_EXP
import com.garan.skipjack.SCALE_NOTES_NUM

/**
 * Defines a tuning target that is a note in any octave, where the [nearestPitchDifference] is
 * chosen as being in the nearest octave to the pitch passed in.
 */
data class NamedNote(val note: Note) : TuningTarget {
    override val targetNote = note

    override fun nearestPitchDifference(pitch: Float): Double {
        val sampleIndex = kotlin.math.log(pitch / A_ZERO_FREQ, NOTE_EXP)
        var modVal = (sampleIndex - note.scaleOffset) % SCALE_NOTES_NUM
        if (modVal > SCALE_NOTES_NUM / 2) {
            modVal -= SCALE_NOTES_NUM
        }
        return modVal
    }
}
