package com.garan.skipjack

import app.cash.turbine.test
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TunedStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class TuningRepositoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun namedNoteA_ExactlyTuned() = runTest {
        val repo = TuningRepository(
            FakeAudioSource(
                listOf(440f)
            )
        )
        repo.setTuningConfig(TuningConfig.WHOLE_NOTES)

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
                // G2
                listOf(98f)
            )
        )
        // Set the TuningRepo to include only one Tuning target: A4
        repo.setTuningConfig(TuningConfig.VIOLIN)

        repo.tuningStatusFlow.test {
            val firstItem = awaitItem()
            Assert.assertTrue(firstItem is TunedStatus.TuningInfo)
            val info = firstItem as TunedStatus.TuningInfo
            Assert.assertEquals(Note.G, info.note)
            // Received pitch is a whole octave below the target
            Assert.assertEquals(-12.0, info.pitchDifference, 0.01)
            awaitComplete()
        }
    }
}