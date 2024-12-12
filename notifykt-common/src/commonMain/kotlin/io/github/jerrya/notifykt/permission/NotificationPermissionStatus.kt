package io.github.jerrya.notifykt.permission

interface NotificationPermissionStatus {
    suspend fun getNotificationPermissionStatus(): Status

    suspend fun isRegisteredForNotifications(): Boolean =
        getNotificationPermissionStatus() == Status.Authorized

    sealed interface Status {
        data object NotDetermined : Status
        data object Authorized : Status
        data object Denied : Status
        data object Error : Status
    }
}