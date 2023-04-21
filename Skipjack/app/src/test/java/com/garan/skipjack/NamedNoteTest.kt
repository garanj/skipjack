package com.garan.skipjack

import com.garan.skipjack.model.NamedNote
import com.garan.skipjack.model.Note
import org.junit.Assert
import org.junit.Test

class NamedNoteTest {
    val DIFFERENCE_TOLERANCE = 0.01

    @Test
    fun nearestPitchDifference_NoteAzero_equal() {
        val note = NamedNote(Note.A)
        Assert.assertEquals(0.0, note.nearestPitchDifference(27.50000f), DIFFERENCE_TOLERANCE)
    }

    // Test all notes against correct pitch in octave 6
    @Test
    fun nearestPitchDifference_NoteAsix_equal() {
        val note = NamedNote(Note.A)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1760.000f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteBsix_equal() {
        val note = NamedNote(Note.B)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1975.533f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteCsix_equal() {
        val note = NamedNote(Note.C)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1046.502f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteDsix_equal() {
        val note = NamedNote(Note.D)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1174.659f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteEsix_equal() {
        val note = NamedNote(Note.E)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1318.510f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteFsix_equal() {
        val note = NamedNote(Note.F)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1396.913f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteGsix_equal() {
        val note = NamedNote(Note.G)
        Assert.assertEquals(0.0, note.nearestPitchDifference(1567.982f), DIFFERENCE_TOLERANCE)
    }

    // Test pitches above and below.
    @Test
    fun nearestPitchDifference_NoteCsix_3semitonesFromA() {
        val note = NamedNote(Note.A)
        // 1046.502f is C6, which is 3 semitones above an A
        Assert.assertEquals(3.0, note.nearestPitchDifference(1046.502f), DIFFERENCE_TOLERANCE)
    }

    @Test
    fun nearestPitchDifference_NoteGfive_equal() {
        val note = NamedNote(Note.A)
        // 391.9954f is G4, which is 2 semitones below an A
        Assert.assertEquals(-2.0, note.nearestPitchDifference(391.9954f), DIFFERENCE_TOLERANCE)
    }
}