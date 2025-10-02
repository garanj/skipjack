package com.garan.skipjack

import kotlin.math.pow

// How long to wait with no recognized note before auto-exiting
const val NO_TUNED_NOTE_AUTO_EXIT_TIMEOUT_MS = 30_000L

// How long to wait on a given note detection before returning to untuned state.
const val TUNING_NOTE_TIMEOUT_MS = 5000L

const val SCALE_NOTES_NUM = 12

// Exponent used to calculate next tone up from given tone.
val NOTE_EXP = 2.0.pow((1.0 / SCALE_NOTES_NUM))

// All tuning is relative to A0
const val A_ZERO_FREQ = 27.50000
const val PITCH_PROBABILITY_MIN = 0.95

const val EXTRA_TUNING_CONFIG_NAME = "tuning_config_name"
