package com.garan.skipjack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.garan.skipjack.definitions.TuningConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Settings @Inject constructor(
    @ApplicationContext val context: Context,
) {
    val mostRecentInstrument: Flow<TuningConfig> = context.dataStore.data.map { prefs ->
        val configId = prefs[recentInstrumentId] ?: TuningConfig.VIOLIN.name
        TuningConfig.valueOf(configId)
    }

    suspend fun setMostRecentInstrument(config: TuningConfig) {
        if (config != TuningConfig.WHOLE_NOTES) {
            context.dataStore.edit { prefs ->
                prefs[recentInstrumentId] = config.name
            }
        }
    }

    companion object {
        private val recentInstrumentId = stringPreferencesKey("RECENT_INSTRUMENT_ID")
    }
}
