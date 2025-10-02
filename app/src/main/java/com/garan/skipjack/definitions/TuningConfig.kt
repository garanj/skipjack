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
package com.garan.skipjack.definitions

import com.garan.skipjack.R
import com.garan.skipjack.model.ExactNote
import com.garan.skipjack.model.NamedNote
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TuningTarget

enum class TuningConfig {
    NONE {
        override val labelResId = R.string.instrument_def_none
        override val notes = listOf<TuningTarget>()
        override val isInstrument = false
    },
    WHOLE_NOTES {
        override val labelResId = R.string.instrument_def_whole_notes
        override val notes = listOf(
            NamedNote(Note.A),
            NamedNote(Note.B),
            NamedNote(Note.C),
            NamedNote(Note.D),
            NamedNote(Note.E),
            NamedNote(Note.F),
            NamedNote(Note.G),
        )
        override val isInstrument = false
    },
    ALL_NOTES {
        override val labelResId = R.string.instrument_def_all_notes
        override val notes = listOf(
            NamedNote(Note.A),
            NamedNote(Note.A_SHARP),
            NamedNote(Note.B),
            NamedNote(Note.C),
            NamedNote(Note.C_SHARP),
            NamedNote(Note.D),
            NamedNote(Note.D_SHARP),
            NamedNote(Note.E),
            NamedNote(Note.F),
            NamedNote(Note.F_SHARP),
            NamedNote(Note.G),
            NamedNote(Note.G_SHARP),
        )
        override val isInstrument = false
    },
    VIOLIN {
        override val labelResId = R.string.instrument_def_violin
        override val notes = listOf(
            ExactNote(Note.G, 3),
            ExactNote(Note.D, 4),
            ExactNote(Note.A, 4),
            ExactNote(Note.E, 5),
        )
    },
    VIOLA {
        override val labelResId = R.string.instrument_def_viola
        override val notes = listOf(
            ExactNote(Note.C, 3),
            ExactNote(Note.G, 3),
            ExactNote(Note.D, 4),
            ExactNote(Note.A, 4),
        )
    },
    UKELELE {
        override val labelResId = R.string.instrument_def_ukelele
        override val notes = listOf(
            ExactNote(Note.G, 4),
            ExactNote(Note.C, 4),
            ExactNote(Note.E, 4),
            ExactNote(Note.A, 4),
        )
    },
    CELLO {
        override val labelResId = R.string.instrument_def_cello
        override val notes = listOf(
            ExactNote(Note.C, 2),
            ExactNote(Note.G, 2),
            ExactNote(Note.D, 3),
            ExactNote(Note.A, 3),
        )
    },
    DOUBLE_BASS {
        override val labelResId = R.string.instrument_def_double_bass
        override val notes = listOf(
            ExactNote(Note.E, 1),
            ExactNote(Note.A, 1),
            ExactNote(Note.D, 2),
            ExactNote(Note.G, 2),
        )
    },
    GUITAR {
        override val labelResId = R.string.instrument_def_guitar
        override val notes = listOf(
            ExactNote(Note.E, 2),
            ExactNote(Note.A, 2),
            ExactNote(Note.D, 3),
            ExactNote(Note.G, 3),
            ExactNote(Note.B, 3),
            ExactNote(Note.E, 4),
        )
    },
    BANJO {
        override val labelResId = R.string.instrument_def_banjo
        override val notes = listOf(
            ExactNote(Note.G, 4),
            ExactNote(Note.D, 3),
            ExactNote(Note.G, 3),
            ExactNote(Note.B, 3),
            ExactNote(Note.D, 4),
        )
    }, ;

    abstract val labelResId: Int
    open val isInstrument: Boolean = true
    abstract val notes: List<TuningTarget>
}
