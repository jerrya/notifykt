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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.jerrya.notifykt.PermissionRequestState
import io.github.jerrya.notifykt.rememberNotificationPermission
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var permissionStatusText by remember { mutableStateOf("") }
        var permissionRequest = rememberNotificationPermission {
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