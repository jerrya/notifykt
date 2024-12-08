package io.github.jerrya.notifykt


import androidx.compose.runtime.Composable
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.resume

actual suspend fun getNotificationPermissionStatus() =
    suspendCancellableCoroutine<NotificationPermissionStatus> {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
            val status = when (settings?.authorizationStatus) {
                UNAuthorizationStatusNotDetermined -> NotificationPermissionStatus.NotDetermined
                UNAuthorizationStatusAuthorized -> NotificationPermissionStatus.Authorized
                UNAuthorizationStatusDenied -> NotificationPermissionStatus.Denied
                else -> NotificationPermissionStatus.Error
            }
            it.resume(status)
        }
    }

@Composable
actual fun rememberNotificationPermission(
    onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit
): PermissionRequestState {
    return IosPermissionRequestState(onComplete = onComplete)
}

class IosPermissionRequestState(
    private val onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit,
) : PermissionRequestState {

    override fun launchPermissionRequest() {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
        notificationCenter.requestAuthorizationWithOptions(
            UNAuthorizationOptionAlert
                .or(UNAuthorizationOptionBadge)
                .or(UNAuthorizationOptionSound)
        ) { success, error ->
            if (success) {
                onComplete(PermissionRequestState.PermissionRequestStatus.Authorized)
            } else {
//                onComplete(NotificationPermissionPromptResult.Denied(error?.localizedDescription.toString()))
                onComplete(PermissionRequestState.PermissionRequestStatus.Denied)
            }
        }
    }

}