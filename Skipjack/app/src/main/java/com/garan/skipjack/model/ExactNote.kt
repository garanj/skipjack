package com.garan.skipjack.model

import com.garan.skipjack.A_ZERO_FREQ
import com.garan.skipjack.NOTE_EXP
import com.garan.skipjack.SCALE_NOTES_NUM

/**
 * Defines a tuning target that is a note and octave, e.g. A4 as distinct from A3.
 */
data class ExactNote(val note: Note, val octave: Int) : TuningTarget {
    override val targetNote = note

    override fun nearestPitchDifference(pitch: Float): Double {
        val sampleIndex = kotlin.math.log(pitch / A_ZERO_FREQ, NOTE_EXP)
        return sampleIndex - (note.scaleOffset + SCALE_NOTES_NUM * octave)
    }
}