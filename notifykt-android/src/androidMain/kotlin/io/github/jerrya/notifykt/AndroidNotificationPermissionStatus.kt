package io.github.jerrya.notifykt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import io.github.jerrya.notifykt.context.ContextProvider
import io.github.jerrya.notifykt.permission.NotificationPermissionStatus

class AndroidNotificationPermissionStatus : NotificationPermissionStatus {
    override suspend fun getNotificationPermissionStatus(): NotificationPermissionStatus.Status {
        return ContextProvider.getInstance().context.let { context ->
            val isGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                NotificationManagerCompat.from(context).areNotificationsEnabled()
            }
            if (isGranted) NotificationPermissionStatus.Status.Authorized else NotificationPermissionStatus.Status.NotDetermined
        } ?: NotificationPermissionStatus.Status.Error
    }
}