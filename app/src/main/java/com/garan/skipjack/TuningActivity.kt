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
package com.garan.skipjack

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.presentation.TuningScreen
import com.garan.skipjack.theme.SkipjackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TuningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configId = intent?.extras?.getString(EXTRA_TUNING_CONFIG_NAME)
        val config = if (configId != null) {
            TuningConfig.valueOf(configId)
        } else {
            TuningConfig.WHOLE_NOTES
        }
        setContent {
            SkipjackTheme {
                TuningScreen(config)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setScreenOn()
    }

    override fun onPause() {
        super.onPause()
        setScreenOff()
    }

    // TODO: Move to compose modifier
    private fun setScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun setScreenOff() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
