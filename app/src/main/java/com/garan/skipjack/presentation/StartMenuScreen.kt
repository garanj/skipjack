package com.garan.skipjack.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.skipjack.R
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.theme.SkipjackTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun StartMenuScreen(
    onTuningClick: (TuningConfig) -> Unit
) {
    val context = LocalContext.current
    val instrumentMap = remember {
        TuningConfig.entries.associateBy { context.getString(it.labelResId) }.toSortedMap()
    }
    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.SingleButton
        )
    )

    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(
            columnState = columnState
        ) {
            item {
                ResponsiveListHeader(contentPadding = firstItemPadding()) {
                    Text(text = stringResource(R.string.instruments))
                }
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onTuningClick(TuningConfig.WHOLE_NOTES) }
                ) {
                    Text(stringResource(id = R.string.instrument_def_whole_notes))
                }
            }
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onTuningClick(TuningConfig.ALL_NOTES) }
                ) {
                    Text(stringResource(id = R.string.instrument_def_all_notes))
                }
            }
            instrumentMap
                .filter { it.value.isInstrument }
                .forEach { instrument ->
                    item {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.secondaryButtonColors(),
                            onClick = {
                                onTuningClick(instrument.value)
                            }
                        ) {
                            Text(text = instrument.key)
                        }
                    }
                }
        }
    }
}

@WearPreviewDevices
@Composable
fun StartMenuPreview(modifier: Modifier = Modifier) {
    SkipjackTheme {
        StartMenuScreen {}
    }
}