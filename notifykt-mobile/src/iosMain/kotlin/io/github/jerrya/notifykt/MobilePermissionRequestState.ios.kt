package io.github.jerrya.notifykt

import androidx.compose.runtime.Composable

@Composable
actual fun rememberNotificationPermission(
    onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit
): PermissionRequestState {
    return IosPermissionRequestState(onComplete)
}