package com.garan.skipjack.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garan.skipjack.TuningRepository
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.model.TunedStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PitchMeterViewModel @Inject constructor(private val tuningRepository: TuningRepository) :
    ViewModel() {

    val tuningStatusFlow = tuningRepository.tuningStatusFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TunedStatus.NoTuningInfo)

    fun setTuningNoteGroup(tuningConfig: TuningConfig) {
        tuningRepository.setTuningConfig(tuningConfig)
    }
}
