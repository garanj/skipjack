package com.garan.skipjack.model

/**
 * A target for tuning; The [Note] is specified, and the single method provides the pitch difference
 * between the specified pitch and the target note.
 */
interface TuningTarget {
    val targetNote: Note

    fun nearestPitchDifference(pitch: Float): Double
}





