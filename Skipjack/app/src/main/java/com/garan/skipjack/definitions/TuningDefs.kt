package com.garan.skipjack.definitions

import com.garan.skipjack.model.NamedNote
import com.garan.skipjack.model.Note
import com.garan.skipjack.model.TuningConfigurationGroup

// Tuning definition to match any note, in any octave.
val wholeOctaveNamedNotes = TuningConfigurationGroup(
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