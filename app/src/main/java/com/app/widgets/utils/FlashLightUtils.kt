package com.app.widgets.utils

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager

fun Context.toggleFlashLight(toggle: Boolean): Boolean {
    val isFlashAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
    if (!isFlashAvailable) return false

    val cameraManager: CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId: String = try {
        cameraManager.cameraIdList.first()
    } catch (e: Exception) {
        loge("exception", e)
        null
    } ?: return false

    return try {
        cameraManager.setTorchMode(cameraId, toggle)
        toggle
    } catch (e: Exception) {
        loge("exception", e)
        false
    }
}