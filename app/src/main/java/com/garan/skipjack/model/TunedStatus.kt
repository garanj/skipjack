package com.garan.skipjack.model

sealed class TunedStatus {
    data class TuningInfo(
        val note: Note,
        val pitchDifference: Double,
    ) : TunedStatus()

    object NoTuningInfo : TunedStatus()
}
