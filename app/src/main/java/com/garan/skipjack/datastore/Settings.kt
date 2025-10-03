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
    @param:ApplicationContext val context: Context,
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
