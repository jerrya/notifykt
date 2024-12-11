package io.github.jerrya.notifykt

//import androidx.compose.runtime.Composable
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

//@Composable
fun rememberNotificationPermission(
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