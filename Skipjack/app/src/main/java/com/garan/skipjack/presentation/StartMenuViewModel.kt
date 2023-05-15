package com.garan.skipjack.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.wear.tiles.TileService
import com.garan.skipjack.datastore.Settings
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.tile.QuickAccessTile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartMenuViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val settings: Settings
) :
    ViewModel() {

    val mostRecentConfig = settings.mostRecentInstrument
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TuningConfig.ALL_NOTES)

    fun setMostRecentConfig(config: TuningConfig) {
        viewModelScope.launch {
            settings.setMostRecentInstrument(config)
            TileService.getUpdater(context)
                .requestUpdate(QuickAccessTile::class.java)
        }
    }
}