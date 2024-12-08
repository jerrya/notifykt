package io.github.jerrya.notifykt

interface PermissionRequestState {
    fun launchPermissionRequest()

    sealed interface PermissionRequestStatus {
        data object Authorized : PermissionRequestStatus
        data object Denied : PermissionRequestStatus
        data object NotDetermined : PermissionRequestStatus
    }
}