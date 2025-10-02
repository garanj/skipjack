package com.garan.skipjack.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.skipjack.R
import com.garan.skipjack.theme.SkipjackTheme

@Composable
fun PermissionRequiredScreen(
    onPermissionClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(0.707f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(
                        id = R.string.permissions_explanation,
                        stringResource(id = R.string.app_name)
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption1
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onPermissionClick
                ) {
                    Text(
                        text = stringResource(R.string.show_permissions)
                    )
                }
            }
        }
    }
}

@WearPreviewDevices
@Composable
fun PermissionRequiredPreview() {
    SkipjackTheme {
        PermissionRequiredScreen(onPermissionClick = {})
    }
}
