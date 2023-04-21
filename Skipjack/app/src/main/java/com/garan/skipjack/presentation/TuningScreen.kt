package com.garan.skipjack.presentation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.garan.skipjack.definitions.wholeOctaveNamedNotes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TuningScreen() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    if (permissionState.status == PermissionStatus.Granted) {
        val viewModel = hiltViewModel<PitchMeterViewModel>()
        val tunedStatus by viewModel.tuningStatusFlow.collectAsState()
        PitchMeterScreen(status = tunedStatus)
        LaunchedEffect(Unit) {
            // Hard-code whole-octave group for now. TODO: Add ability to select groups for specific
            // instruments, incorporating ExactNotes as well.
            viewModel.setTuningNoteGroup(wholeOctaveNamedNotes)
        }
    } else {
        PermissionRequiredScreen(
            onPermissionClick = { permissionState.launchPermissionRequest() }
        )
    }
}