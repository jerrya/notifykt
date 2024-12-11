package io.github.jerrya.notifykt

import androidx.compose.runtime.Composable

@Composable
expect fun rememberNotificationPermission(
    onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit
): PermissionRequestState
