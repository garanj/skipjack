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
package com.garan.skipjack.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import com.garan.skipjack.EXTRA_TUNING_CONFIG_NAME
import com.garan.skipjack.TuningActivity
import com.garan.skipjack.presentation.StartMenuScreen
import com.garan.skipjack.presentation.StartMenuViewModel

@Composable
fun SkipjackNavigation() {
    val context = LocalContext.current
    val backStack = rememberNavBackStack(StartMenu)

    AppScaffold {
        NavDisplay(
            backStack = backStack,
            sceneStrategies = listOf(rememberSwipeDismissableSceneStrategy()),
            entryProvider = entryProvider {
                entry<StartMenu> {
                    val viewModel = hiltViewModel<StartMenuViewModel>()
                    StartMenuScreen(
                        onTuningClick = { config ->
                            viewModel.setMostRecentConfig(config)
                            launchTuningActivity(context, config.name)
                        },
                    )
                }
            },
        )
    }
}

private fun launchTuningActivity(context: Context, configName: String) {
    val intent = Intent(context, TuningActivity::class.java).apply {
        putExtra(EXTRA_TUNING_CONFIG_NAME, configName)
    }
    context.startActivity(intent)
}
