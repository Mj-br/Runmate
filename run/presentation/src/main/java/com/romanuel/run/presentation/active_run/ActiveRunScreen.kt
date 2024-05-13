@file:OptIn(ExperimentalMaterial3Api::class)

package com.romanuel.run.presentation.active_run

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanuel.core.presentation.designsystem.RunmateTheme
import com.romanuel.core.presentation.designsystem.StartIcon
import com.romanuel.core.presentation.designsystem.StopIcon
import com.romanuel.core.presentation.designsystem.components.RunmateActionButton
import com.romanuel.core.presentation.designsystem.components.RunmateDialog
import com.romanuel.core.presentation.designsystem.components.RunmateFloatingActionButton
import com.romanuel.core.presentation.designsystem.components.RunmateOutlinedActionButton
import com.romanuel.core.presentation.designsystem.components.RunmateScaffold
import com.romanuel.core.presentation.designsystem.components.RunmateToolbar
import com.romanuel.run.presentation.R
import com.romanuel.run.presentation.active_run.components.RunDataCard
import com.romanuel.run.presentation.active_run.maps.TrackerMap
import com.romanuel.run.presentation.active_run.service.ActiveRunService
import com.romanuel.run.presentation.util.hasLocationPermission
import com.romanuel.run.presentation.util.hasNotificationPermission
import com.romanuel.run.presentation.util.shouldShowLocationPermissionRationale
import com.romanuel.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel(),
) {
    ActiveRunScreen(
        state = viewModel.state,
        onServiceToggle = onServiceToggle,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
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
            val showLocationRationale = activity.shouldShowLocationPermissionRationale()
            val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

            onAction(
                ActiveRunAction.SubmitLocationPermissionInfo(
                    acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                    showLocationRationale = showLocationRationale
                )
            )
            onAction(
                ActiveRunAction.SubmitNotificationPermissionInfo(
                    acceptedNotificationPermission = hasNotificationPermission,
                    showNotificationPermissionRationale = showNotificationRationale,
                )
            )
        }

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRunmatePermissions(context)
        }
    }

    LaunchedEffect(key1 = state.isRunFinished) {
        if (state.isRunFinished) onServiceToggle(false)
    }

    LaunchedEffect(key1 = state.shouldTrack) {
        if (context.hasLocationPermission() && state.shouldTrack
            && !ActiveRunService.isServiceActive
        ) {
            onServiceToggle(true)
        }
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
            TrackerMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = {},
                modifier = Modifier.fillMaxSize()
            )
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

    if (!state.shouldTrack && state.hasStartedRunning) {
        RunmateDialog(
            title = stringResource(id = R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(id = R.string.resume_or_finish_run),
            primaryButton = {
                RunmateActionButton(
                    text = stringResource(id = R.string.resume),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnResumeRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            },
            secondaryButton = {
                RunmateOutlinedActionButton(
                    text = stringResource(id = R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = { onAction(ActiveRunAction.OnFinishRunClick) },
                    modifier = Modifier.weight(1f)
                )
            }
        )
    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RunmateDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            primaryButton = {
                RunmateOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationaleDialog)
                        permissionLauncher.requestRunmatePermissions(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRunmatePermissions(
    context: Context,
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION,
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermission)
        }

        !hasLocationPermission -> launch(locationPermissions)
        !hasNotificationPermission -> launch(notificationPermission)
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RunmateTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}
        )
    }
}
