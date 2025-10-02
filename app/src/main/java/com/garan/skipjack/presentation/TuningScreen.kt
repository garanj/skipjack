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
package com.garan.skipjack.presentation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.garan.skipjack.definitions.TuningConfig
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TuningScreen(
    config: TuningConfig,
) {
    val viewModel = hiltViewModel<PitchMeterViewModel>()
    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    if (permissionState.status == PermissionStatus.Granted) {

        val tunedStatus by viewModel.tuningStatusFlow.collectAsState()
        PitchMeterScreen(status = tunedStatus)
        LaunchedEffect(Unit) {
            viewModel.setTuningNoteGroup(config)
        }
    } else {
        PermissionRequiredScreen(
            onPermissionClick = { permissionState.launchPermissionRequest() },
        )
    }
}
