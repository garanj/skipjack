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

import com.garan.skipjack.model.ExactNote
import com.garan.skipjack.model.Note
import org.junit.Assert
import org.junit.Test

class ExactNoteTest {
    private val differenceTolerance = 0.01

    @Test
    fun nearestPitchDifference_NoteAzero_equal() {
        val note = ExactNote(Note.A, 0)
        Assert.assertEquals(0.0, note.nearestPitchDifference(27.50000f), differenceTolerance)
    }

    @Test
    fun nearestPitchDifference_NoteGfour_equal() {
        val note = ExactNote(Note.G, 4)
        Assert.assertEquals(0.0, note.nearestPitchDifference(391.9954f), differenceTolerance)
    }

    @Test
    fun nearestPitchDifference_NoteCfour_equal() {
        val note = ExactNote(Note.C, 4)
        Assert.assertEquals(0.0, note.nearestPitchDifference(261.6256f), differenceTolerance)
    }

    @Test
    fun nearestPitchDifference_NoteCfour_Dpitch_equal() {
        val note = ExactNote(Note.C, 4)
        // 293.6648f is D4, which is 2 semitones above C4
        Assert.assertEquals(2.0, note.nearestPitchDifference(293.6648f), differenceTolerance)
    }
}
