package io.github.jerrya.notifykt


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

actual suspend fun getNotificationPermissionStatus(): NotificationPermissionStatus {
    return ContextProvider.getInstance().context.let { context ->
        val isGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
        if (isGranted) NotificationPermissionStatus.Authorized else NotificationPermissionStatus.NotDetermined
    } ?: NotificationPermissionStatus.Error
}

@Composable
actual fun rememberNotificationPermission(
    onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit,
): PermissionRequestState {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onComplete(PermissionRequestState.PermissionRequestStatus.Authorized)
            } else {
                onComplete(PermissionRequestState.PermissionRequestStatus.Denied)
            }
        }

    return remember {
        MutablePermissionRequestState(
            launcher,
            onComplete
        )
    }
}

class MutablePermissionRequestState(
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val onComplete: (PermissionRequestState.PermissionRequestStatus) -> Unit,
) : PermissionRequestState {

    override fun launchPermissionRequest() {
        if (Build.VERSION.SDK_INT >= 33) {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            onComplete(PermissionRequestState.PermissionRequestStatus.Authorized)
        }
    }
}