package io.github.jerrya.notifykt

import androidx.compose.runtime.Composable

sealed interface NotificationPermissionStatus {
    data object NotDetermined : NotificationPermissionStatus
    data object Authorized : NotificationPermissionStatus
    data object Denied : NotificationPermissionStatus
    data object Error : NotificationPermissionStatus
}

expect suspend fun getNotificationPermissionStatus(): NotificationPermissionStatus

suspend fun isRegisteredForNotifications(): Boolean =
    getNotificationPermissionStatus() == NotificationPermissionStatus.Authorized

interface NotificationPermission {
    fun launchRequest()
}

@Composable
expect fun rememberNotificationPermission(onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit): PermissionRequestState

interface PermissionRequestState {
    fun launchPermissionRequest()

    sealed interface PermissionRequestStatus {
        data object Authorized : PermissionRequestStatus
        data object Denied : PermissionRequestStatus
        data object NotDetermined : PermissionRequestStatus
    }
}