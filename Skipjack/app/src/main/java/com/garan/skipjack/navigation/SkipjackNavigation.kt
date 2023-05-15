package com.garan.skipjack.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.activity
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.garan.skipjack.EXTRA_TUNING_CONFIG_NAME
import com.garan.skipjack.TuningActivity
import com.garan.skipjack.presentation.StartMenuScreen
import com.garan.skipjack.presentation.StartMenuViewModel
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scrollable


@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun SkipjackNavigation() {
    val navController = rememberSwipeDismissableNavController()

    WearNavScaffold(
        startDestination = Screens.START_MENU.route,
        navController = navController,
    ) {
        scrollable(
            route = Screens.START_MENU.route
        ) {
            val viewModel = hiltViewModel<StartMenuViewModel>()
            StartMenuScreen(
                onTuningClick = { config ->
                    viewModel.setMostRecentConfig(config)
                    navController.navigate(
                        route = Screens.TUNING_SCREEN.route + "/" + config.name
                    )
                },
                columnState = it.columnState
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