package io.github.jerrya.notifykt

import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun androidRememberNotificationPermission(
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