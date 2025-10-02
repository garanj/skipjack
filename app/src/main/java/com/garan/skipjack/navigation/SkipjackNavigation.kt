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

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.activity
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.garan.skipjack.EXTRA_TUNING_CONFIG_NAME
import com.garan.skipjack.TuningActivity
import com.garan.skipjack.presentation.StartMenuScreen
import com.garan.skipjack.presentation.StartMenuViewModel

@Composable
fun SkipjackNavigation() {
    val navController = rememberSwipeDismissableNavController()

    AppScaffold {
        SwipeDismissableNavHost(
            startDestination = Screens.START_MENU.route,
            navController = navController,
        ) {
            composable(
                route = Screens.START_MENU.route,
            ) {
                val viewModel = hiltViewModel<StartMenuViewModel>()
                StartMenuScreen(
                    onTuningClick = { config ->
                        viewModel.setMostRecentConfig(config)
                        navController.navigate(
                            route = Screens.TUNING_SCREEN.route + "/" + config.name,
                        )
                    },
                )
            }
            // This destination is modelled as a separate Activity: This is because using a single
            // -Activity application makes it seemingly impossible to launch from a Tile without
            // messing up the Recents entry (see [QuickAccessTile] for the construction of the
            // synthetic backstack used when launching from the Tile.
            activity(Screens.TUNING_SCREEN.route + "/{$EXTRA_TUNING_CONFIG_NAME}") {
                this.activityClass = TuningActivity::class
            }
        }
    }
}
