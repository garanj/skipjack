package com.garan.skipjack.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.skipjack.R
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.theme.SkipjackTheme
import com.google.android.horologist.compose.layout.ColumnItemType
import com.google.android.horologist.compose.layout.rememberResponsiveColumnPadding

@Composable
fun StartMenuScreen(
    onTuningClick: (TuningConfig) -> Unit,
) {
    val context = LocalContext.current
    val instrumentMap = remember {
        TuningConfig.entries.associateBy { context.getString(it.labelResId) }.toSortedMap()
    }
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        // Use workaround from Horologist for padding or wait until fix lands
        contentPadding =
        rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton,
        ),
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding,
        ) {
            item {
                ListHeader(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                ) { Text(text = stringResource(R.string.instruments)) }
            }
            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                    onClick = { onTuningClick(TuningConfig.WHOLE_NOTES) },
                ) {
                    Text(stringResource(id = R.string.instrument_def_whole_notes))
                }
            }
            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                    onClick = { onTuningClick(TuningConfig.ALL_NOTES) },
                ) {
                    Text(stringResource(id = R.string.instrument_def_all_notes))
                }
            }
            instrumentMap
                .filter { it.value.isInstrument }
                .forEach { instrument ->
                    item {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                            colors = ButtonDefaults.filledTonalButtonColors(),
                            onClick = {
                                onTuningClick(instrument.value)
                            },
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
