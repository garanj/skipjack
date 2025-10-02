package com.garan.skipjack.tile

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DeviceParametersBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.DimensionBuilders.dp
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Chip
import androidx.wear.protolayout.material.ChipColors
import androidx.wear.protolayout.material.CompactChip
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.garan.skipjack.EXTRA_TUNING_CONFIG_NAME
import com.garan.skipjack.MainActivity
import com.garan.skipjack.R
import com.garan.skipjack.TuningActivity
import com.garan.skipjack.datastore.Settings
import com.garan.skipjack.definitions.TuningConfig
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService

import com.google.android.horologist.tiles.images.drawableResToImageResource
import kotlinx.coroutines.flow.first

const val RESOURCES_VERSION = "100"
const val RECENT_INSTRUMENT_BUTTON_ID = "RECENT_INSTRUMENT_BUTTON_ID"
const val WHOLE_NOTES_BUTTON_ID = "WHOLE_NOTES_BUTTON_ID"
const val MORE_BUTTON_ID = "MORE_BUTTON_ID"

@OptIn(ExperimentalHorologistApi::class)
class QuickAccessTile() : SuspendingTileService() {
    private val settings by lazy { Settings(this) }

    private val primaryColor = android.graphics.Color.parseColor("#F6D70B")
    private val onPrimaryColor = android.graphics.Color.parseColor("#000000")

    private val LAST_INSTRUMENT_ICON_ID = "last_instrument_id"
    private val WHOLE_NOTES_ICON_ID = "whole_notes_icon_id"

    private val buttonIds = setOf(
        RECENT_INSTRUMENT_BUTTON_ID,
        WHOLE_NOTES_BUTTON_ID,
        MORE_BUTTON_ID,
    )

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        val recentInstrument = settings.mostRecentInstrument.first()

        // This is a workaround to build a backstack containing the parent activity when launching
        // a Tile.
        if (requestParams.currentState.lastClickableId in buttonIds) {
            val intent = when (requestParams.currentState.lastClickableId) {
                RECENT_INSTRUMENT_BUTTON_ID -> Intent(this, TuningActivity::class.java).apply {
                    putExtra(EXTRA_TUNING_CONFIG_NAME, recentInstrument.name)
                }

                WHOLE_NOTES_BUTTON_ID -> Intent(this, TuningActivity::class.java)
                else -> Intent(this, MainActivity::class.java)
            }
            TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(intent)
                .startActivities()
        }

        return TileBuilders.Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(
                TimelineBuilders.Timeline.fromLayoutElement(
                    buildLayout(requestParams.deviceConfiguration, recentInstrument)
                )
            )
            .build()
    }

    override suspend fun resourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .addIdToImageMapping(
                LAST_INSTRUMENT_ICON_ID,
                drawableResToImageResource(R.drawable.baseline_music_note_24)
            )
            .addIdToImageMapping(
                WHOLE_NOTES_ICON_ID,
                drawableResToImageResource(R.drawable.baseline_piano_24)
            )
            .build()
    }

    private fun buildLayout(
        deviceParameters: DeviceParametersBuilders.DeviceParameters,
        recentInstrument: TuningConfig
    ): PrimaryLayout {
        val lastInstrument = Instrument(
            label = getString(recentInstrument.labelResId),
            iconId = LAST_INSTRUMENT_ICON_ID,
            clickable = createClickable(RECENT_INSTRUMENT_BUTTON_ID)
        )
        val wholeNotes = Instrument(
            label = getString(R.string.instrument_def_whole_notes),
            iconId = WHOLE_NOTES_ICON_ID,
            clickable = createClickable(RECENT_INSTRUMENT_BUTTON_ID)
        )

        return PrimaryLayout.Builder(deviceParameters)
            .setResponsiveContentInsetEnabled(true)
            .setContent(
                LayoutElementBuilders.Column.Builder()
                    .setWidth(DimensionBuilders.ExpandedDimensionProp.Builder().build())
                    .addContent(instrumentChip(this, deviceParameters, lastInstrument))
                    .addContent(LayoutElementBuilders.Spacer.Builder().setHeight(dp(4f)).build())
                    .addContent(instrumentChip(this, deviceParameters, wholeNotes))
                    .build()
            )
            .setPrimaryChipContent(
                CompactChip.Builder(
                    this,
                    getString(R.string.tile_more_button),
                    createClickable(MORE_BUTTON_ID),
                    deviceParameters
                )
                    .setChipColors(
                        ChipColors(
                            /*backgroundColor=*/ ColorBuilders.argb(primaryColor),
                            /*contentColor=*/ ColorBuilders.argb(onPrimaryColor)
                        )
                    )
                    .build()
            )
            .build()
    }

    private fun createClickable(actionId: String) = ModifiersBuilders.Clickable.Builder()
        .setId(actionId)
        .setOnClick(
            ActionBuilders.LoadAction.Builder().build()
        )
        .build()

    private fun instrumentChip(
        context: Context,
        deviceParameters: DeviceParametersBuilders.DeviceParameters,
        instrument: Instrument
    ): Chip {
        return Chip.Builder(context, instrument.clickable, deviceParameters)
            .setWidth(DimensionBuilders.ExpandedDimensionProp.Builder().build())
            .setIconContent(instrument.iconId)
            .setPrimaryLabelContent(instrument.label)
            .setChipColors(
                ChipColors(
                    /*backgroundColor=*/ ColorBuilders.argb(primaryColor),
                    /*contentColor=*/ ColorBuilders.argb(onPrimaryColor)
                )
            )
            .build()
    }

    data class Instrument(
        val label: String,
        val iconId: String,
        val clickable: ModifiersBuilders.Clickable
    )
}