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
package com.garan.skipjack.tile

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material3.ButtonDefaults
import androidx.wear.protolayout.material3.ColorScheme
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.button
import androidx.wear.protolayout.material3.compactButton
import androidx.wear.protolayout.material3.icon
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.primaryLayout
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.types.argb
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.Material3TileService
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import com.garan.skipjack.EXTRA_TUNING_CONFIG_NAME
import com.garan.skipjack.MainActivity
import com.garan.skipjack.R
import com.garan.skipjack.TuningActivity
import com.garan.skipjack.datastore.Settings
import com.garan.skipjack.definitions.TuningConfig
import kotlinx.coroutines.flow.first

const val RESOURCES_VERSION = "100"
const val RECENT_INSTRUMENT_BUTTON_ID = "RECENT_INSTRUMENT_BUTTON_ID"
const val WHOLE_NOTES_BUTTON_ID = "WHOLE_NOTES_BUTTON_ID"
const val MORE_BUTTON_ID = "MORE_BUTTON_ID"

class QuickAccessTile :
    Material3TileService(
        allowDynamicTheme = false,
        defaultColorScheme = tileColorScheme,
    ) {
    private val settings by lazy { Settings(this) }

    private val buttonIds = setOf(
        RECENT_INSTRUMENT_BUTTON_ID,
        WHOLE_NOTES_BUTTON_ID,
        MORE_BUTTON_ID,
    )

    override suspend fun MaterialScope.tileResponse(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        val recentInstrument = settings.mostRecentInstrument.first()

        // This is a workaround to build a backstack containing the parent activity when launching
        // a Tile.
        if (requestParams.currentState.lastClickableId in buttonIds) {
            val intent = when (requestParams.currentState.lastClickableId) {
                RECENT_INSTRUMENT_BUTTON_ID -> Intent(this@QuickAccessTile, TuningActivity::class.java).apply {
                    putExtra(EXTRA_TUNING_CONFIG_NAME, recentInstrument.name)
                }

                WHOLE_NOTES_BUTTON_ID -> Intent(this@QuickAccessTile, TuningActivity::class.java)
                else -> Intent(this@QuickAccessTile, MainActivity::class.java)
            }
            TaskStackBuilder.create(this@QuickAccessTile)
                .addNextIntentWithParentStack(intent)
                .startActivities()
        }

        return TileBuilders.Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(
                TimelineBuilders.Timeline.fromLayoutElement(
                    buildLayout(recentInstrument),
                ),
            )
            .build()
    }

    data class Instrument(
        val label: String,
        val resourceId: Int,
        val clickable: ModifiersBuilders.Clickable,
    )
}

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreview(context: Context) = TilePreviewData { request ->
    TileBuilders.Tile.Builder()
        .setResourcesVersion(RESOURCES_VERSION)
        .setTileTimeline(
            TimelineBuilders.Timeline.fromLayoutElement(
                materialScope(context, request.deviceConfiguration, defaultColorScheme = tileColorScheme) {
                    buildLayout(TuningConfig.GUITAR)
                },
            ),
        )
        .build()
}

private val tileColorScheme = ColorScheme(
    primary = 0xFFF6D70B.toInt().argb,
    onPrimary = 0xFF000000.toInt().argb,
    secondary = 0xFF8BC34A.toInt().argb,
    onSecondary = 0xFF000000.toInt().argb,
    surfaceContainer = 0xFF404040.toInt().argb,
    onSurface = 0xFFF6D70B.toInt().argb,
)

private fun MaterialScope.buildLayout(
    recentInstrument: TuningConfig,
): LayoutElement {
    val lastInstrument = QuickAccessTile.Instrument(
        label = context.getString(recentInstrument.labelResId),
        resourceId = R.drawable.baseline_music_note_24,
        clickable = createClickable(RECENT_INSTRUMENT_BUTTON_ID),
    )
    val wholeNotes = QuickAccessTile.Instrument(
        label = context.getString(R.string.instrument_def_whole_notes),
        resourceId = R.drawable.baseline_piano_24,
        clickable = createClickable(WHOLE_NOTES_BUTTON_ID),
    )

    return primaryLayout(
        mainSlot = {
            LayoutElementBuilders.Column.Builder()
                .setWidth(expand())
                .addContent(
                    ButtonDefaults.run {
                        button(
                            onClick = lastInstrument.clickable,
                            labelContent = { text(lastInstrument.label.layoutString) },
                            iconContent = { icon(lastInstrument.resourceId.toImageResource()) },
                            width = expand(),
                            colors = filledButtonColors(),
                        )
                    },
                )
                .addContent(LayoutElementBuilders.Spacer.Builder().setHeight(dp(4f)).build())
                .addContent(
                    ButtonDefaults.run {
                        button(
                            onClick = wholeNotes.clickable,
                            labelContent = { text(wholeNotes.label.layoutString) },
                            iconContent = { icon(wholeNotes.resourceId.toImageResource()) },
                            width = expand(),
                            colors = filledButtonColors(),
                        )
                    },
                )
                .build()
        },
        bottomSlot = {
            ButtonDefaults.run {
                compactButton(
                    onClick = createClickable(MORE_BUTTON_ID),
                    labelContent = { text(context.getString(R.string.tile_more_button).layoutString) },
                    colors = filledTonalButtonColors(),
                )
            }
        },
    )
}

private fun createClickable(actionId: String) = ModifiersBuilders.Clickable.Builder()
    .setId(actionId)
    .setOnClick(
        ActionBuilders.LoadAction.Builder().build(),
    )
    .build()

private fun Int.toImageResource() = ResourceBuilders.ImageResource.Builder()
    .setAndroidResourceByResId(
        ResourceBuilders.AndroidImageResourceByResId.Builder()
            .setResourceId(this)
            .build(),
    )
    .build()
