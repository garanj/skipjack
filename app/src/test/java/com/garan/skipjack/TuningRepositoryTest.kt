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

import app.cash.turbine.test
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TunedStatus
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class TuningRepositoryTest {
    @Test
    fun namedNoteA_ExactlyTuned() = runTest {
        val repo = TuningRepository(
            FakeAudioSource(
                listOf(440f),
            ),
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

    @Test
    fun exactNoteA_OutOneOctave() = runTest {
        val repo = TuningRepository(
            FakeAudioSource(
                // G2
                listOf(98f),
            ),
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
