package io.github.jerrya.notifykt

import io.github.jerrya.notifykt.permission.NotificationPermissionStatus

fun getNotificationPermissionStatusRepository(): NotificationPermissionStatus {
    return AndroidNotificationPermissionStatus()
}