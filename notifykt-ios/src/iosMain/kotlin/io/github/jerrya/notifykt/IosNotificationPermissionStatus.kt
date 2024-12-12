package io.github.jerrya.notifykt

import io.github.jerrya.notifykt.permission.NotificationPermissionStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.resume

class IosNotificationPermissionStatus : NotificationPermissionStatus {

    override suspend fun getNotificationPermissionStatus() =
        suspendCancellableCoroutine<NotificationPermissionStatus.Status> {
            val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
            notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
                val status = when (settings?.authorizationStatus) {
                    UNAuthorizationStatusNotDetermined -> NotificationPermissionStatus.Status.NotDetermined
                    UNAuthorizationStatusAuthorized -> NotificationPermissionStatus.Status.Authorized
                    UNAuthorizationStatusDenied -> NotificationPermissionStatus.Status.Denied
                    else -> NotificationPermissionStatus.Status.Error
                }
                it.resume(status)
            }
        }
}