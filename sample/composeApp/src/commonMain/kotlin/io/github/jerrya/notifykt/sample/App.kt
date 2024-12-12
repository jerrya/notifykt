package io.github.jerrya.notifykt.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.jerrya.notifykt.PermissionRequestState
import io.github.jerrya.notifykt.getNotificationPermissionStatusRepository
import io.github.jerrya.notifykt.permission.NotificationPermissionStatus
import io.github.jerrya.notifykt.rememberNotificationPermission
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        var permissionStatusText by remember { mutableStateOf("") }
        val permissionRequest = rememberNotificationPermission {
            permissionStatusText = when (it) {
                PermissionRequestState.PermissionRequestStatus.Authorized -> {
                    "Permissions authorized"
                }

                PermissionRequestState.PermissionRequestStatus.Denied -> {
                    "Permissions denied"
                }

                PermissionRequestState.PermissionRequestStatus.NotDetermined -> {
                    "Permissions not determined"
                }
            }
        }

        var currentPermissionStatusText by remember { mutableStateOf("") }
        val permissionStatus = getNotificationPermissionStatusRepository()

        scope.launch {
            currentPermissionStatusText =
                when (permissionStatus.getNotificationPermissionStatus()) {
                    NotificationPermissionStatus.Status.Authorized -> {
                        "Permissions Authorized"
                    }

                    NotificationPermissionStatus.Status.Denied -> {
                        "Permissions Denied"
                    }

                    NotificationPermissionStatus.Status.Error -> {
                        "Permissions Error"
                    }

                    NotificationPermissionStatus.Status.NotDetermined -> {
                        "Permissions Not Determined"
                    }
                }
        }

        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(permissionStatusText)
            Button(
                onClick = { permissionRequest.launchPermissionRequest() }
            ) {
                Text("Request notification permission")
            }
        }
    }
}