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
import com.garan.skipjack.R
import com.garan.skipjack.definitions.TuningConfig
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState

@Composable
fun StartMenuScreen(
    onTuningClick: (TuningConfig) -> Unit,
    columnState: ScalingLazyColumnState
) {
    val context = LocalContext.current
    val instrumentMap = remember {
        TuningConfig.values().associateBy { context.getString(it.labelResId) }.toSortedMap()
    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        columnState = columnState
    ) {
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