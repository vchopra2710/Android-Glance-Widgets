package com.app.widgets.ui.widgets.flashlight

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.app.widgets.ui.widgets.flashlight.FlashlightAction.Companion.FLASH_LIGHT_KEY
import com.app.widgets.utils.logd
import com.app.widgets.utils.loge
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class FlashlightWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = FlashlightWidget

    private val coroutineScope = MainScope()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action ?: return
        if (action == APPWIDGET_UPDATE) return

        coroutineScope.launch {
            val glanceId = GlanceAppWidgetManager(context)
                .getGlanceIds(FlashlightWidget::class.java)
                .firstOrNull()
                ?: return@launch

            updateAppWidgetState(context, glanceId) { prefs ->
                val toggle = intent.extras?.getInt(FLASH_LIGHT_KEY)
                logd("toggle: $toggle")
                prefs[FlashlightWidget.flashLightOnKey] = context.toggleFlashLight(toggle == 0)
                glanceAppWidget.update(context, glanceId)
            }
        }
    }

    private fun Context.toggleFlashLight(toggle: Boolean): Boolean {
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
            cameraManager.setTorchMode(cameraId, !toggle)
            !toggle
        } catch (e: Exception) {
            loge("exception", e)
            false
        }

    }

    companion object {
        private const val APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS"
        const val CHANGE_FLASH_LIGHT_STATE = "change_flash_light_state"
    }
}