package com.romanuel.run.presentation.util

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.checkSelfPermission

fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)
}

fun ComponentActivity.shouldShowNotificationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 33 &&
            shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)
}

private fun Context.hasPermission(permission: String): Boolean {
    return checkSelfPermission(
        this,
        permission
    ) == PERMISSION_GRANTED
}

fun Context.hasLocationPermission(): Boolean {
    return hasPermission(ACCESS_FINE_LOCATION)
}

fun Context.hasNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= 33) {
        hasPermission(POST_NOTIFICATIONS)
    } else true
}
