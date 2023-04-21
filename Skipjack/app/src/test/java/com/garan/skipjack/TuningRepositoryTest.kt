package com.garan.skipjack

import app.cash.turbine.test
import com.garan.skipjack.model.ExactNote
import com.garan.skipjack.model.NamedNote
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TunedStatus
import com.garan.skipjack.model.TuningConfigurationGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class TuningRepositoryTest {
    private val allNamedNotes = TuningConfigurationGroup(
        listOf(
            NamedNote(Note.A),
            NamedNote(Note.B),
            NamedNote(Note.C),
            NamedNote(Note.D),
            NamedNote(Note.E),
            NamedNote(Note.F),
            NamedNote(Note.G)
        )
    )

    private val a4only = TuningConfigurationGroup(
        listOf(
            ExactNote(Note.A, 4)
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun namedNoteA_ExactlyTuned() = runTest {
        val repo = TuningRepository(
            FakeAudioSource(
                listOf(440f)
            )
        )
        repo.setTuningGroup(allNamedNotes)

        repo.tuningStatusFlow.test {
            val firstItem = awaitItem()
            Assert.assertTrue(firstItem is TunedStatus.TuningInfo)
            val info = firstItem as TunedStatus.TuningInfo
            Assert.assertEquals(Note.A, info.note)
            Assert.assertEquals(0.0, info.pitchDifference, 0.01)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun exactNoteA_OutOneOctave() = runTest {
        val repo = TuningRepository(
            FakeAudioSource(
                // A3
                listOf(220f)
            )
        )
        // Set the TuningRepo to include only one Tuning target: A4
        repo.setTuningGroup(a4only)

        repo.tuningStatusFlow.test {
            val firstItem = awaitItem()
            Assert.assertTrue(firstItem is TunedStatus.TuningInfo)
            val info = firstItem as TunedStatus.TuningInfo
            Assert.assertEquals(Note.A, info.note)
            // Received pitch is a whole octave below the target
            Assert.assertEquals(-12.0, info.pitchDifference, 0.01)
            awaitComplete()
        }
    }
}