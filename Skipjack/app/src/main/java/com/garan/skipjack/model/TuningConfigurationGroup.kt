package com.garan.skipjack.model


data class TuningConfigurationGroup(val notes: List<TuningTarget>) {
    companion object {
        val EMPTY = TuningConfigurationGroup(emptyList())
    }
}