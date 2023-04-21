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

