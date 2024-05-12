@file:OptIn(ExperimentalMaterial3Api::class)

package com.romanuel.run.presentation.active_run

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanuel.core.presentation.designsystem.RunmateTheme
import com.romanuel.core.presentation.designsystem.StartIcon
import com.romanuel.core.presentation.designsystem.StopIcon
import com.romanuel.core.presentation.designsystem.components.RunmateFloatingActionButton
import com.romanuel.core.presentation.designsystem.components.RunmateScaffold
import com.romanuel.core.presentation.designsystem.components.RunmateToolbar
import com.romanuel.run.presentation.R
import com.romanuel.run.presentation.active_run.components.RunDataCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel(),
) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit,
) {

    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = RequestMultiplePermissions()
        ) { permissions ->

            val hasCourseLocationPermission = permissions[ACCESS_COARSE_LOCATION] == true
            val hasFineLocationPermission = permissions[ACCESS_FINE_LOCATION] == true
            val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
                permissions[POST_NOTIFICATIONS] == true
            } else true

            val activity = context as ComponentActivity
        }

    RunmateScaffold(
        withGradient = false,
        topAppBar = {
            RunmateToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            RunmateFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RunmateTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}
