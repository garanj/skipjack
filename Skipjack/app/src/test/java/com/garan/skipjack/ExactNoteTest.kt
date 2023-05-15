package com.garan.skipjack

import com.garan.skipjack.model.ExactNote
import com.garan.skipjack.model.Note
import org.junit.Assert
import org.junit.Test

class ExactNoteTest {
    private val DIFFERENCE_TOLERANCE = 0.01

    @Test
    fun nearestPitchDifference_NoteAzero_equal() {
        val note = ExactNote(Note.A, 0)
        Assert.assertEquals(0.0, note.nearestPitchDifference(27.50000f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteGfour_equal() {
        val note = ExactNote(Note.G, 4)
        Assert.assertEquals(0.0, note.nearestPitchDifference(391.9954f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteCfour_equal() {
        val note = ExactNote(Note.C, 4)
        Assert.assertEquals(0.0, note.nearestPitchDifference(261.6256f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteCfour_Dpitch_equal() {
        val note = ExactNote(Note.C, 4)
        // 293.6648f is D4, which is 2 semitones above C4
        Assert.assertEquals(2.0, note.nearestPitchDifference(293.6648f), DIFFERENCE_TOLERANCE)
    }
}