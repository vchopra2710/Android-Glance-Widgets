package com.app.widgets.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.net.wifi.WifiManager
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.app.widgets.R
import com.app.widgets.receiver.AirplaneStateReceiver
import com.app.widgets.receiver.BluetoothStateReceiver
import com.app.widgets.receiver.WifiStateReceiver
import com.app.widgets.usecase.airplane.GetAirplaneToggleStatusUseCase
import com.app.widgets.usecase.bluetooth.GetBluetoothToggleStatusUseCase
import com.app.widgets.usecase.wifi.GetWifiToggleStatusUseCase
import com.app.widgets.utils.logw
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WidgetService : LifecycleService() {

    private var notificationManager: NotificationManager? = null

    @Inject
    lateinit var wifiStateReceiver: WifiStateReceiver

    @Inject
    lateinit var bluetoothStateReceiver: BluetoothStateReceiver

    @Inject
    lateinit var airplaneStateReceiver: AirplaneStateReceiver

    @Inject
    lateinit var getWifiToggleStatusUseCase: GetWifiToggleStatusUseCase

    @Inject
    lateinit var getBluetoothToggleStatusUseCase: GetBluetoothToggleStatusUseCase

    @Inject
    lateinit var getAirplaneToggleStatusUseCase: GetAirplaneToggleStatusUseCase

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        createForegroundNotification()
        observeWifiToggleState()
        observeBluetoothToggleState()
        observeAirplaneToggleState()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceiver(wifiStateReceiver)
        unregisterBroadcastReceiver(bluetoothStateReceiver)
        unregisterBroadcastReceiver(airplaneStateReceiver)
    }

    private fun observeWifiToggleState() = lifecycleScope.launch(Dispatchers.IO) {
        getWifiToggleStatusUseCase().collect { wifiToggled ->
            if (wifiToggled) registerWifiReceiver()
            else unregisterBroadcastReceiver(wifiStateReceiver)
        }
    }

    private fun observeBluetoothToggleState() = lifecycleScope.launch(Dispatchers.IO) {
        getBluetoothToggleStatusUseCase().collect { bluetoothToggled ->
            if (bluetoothToggled) registerBluetoothRegister()
            else unregisterBroadcastReceiver(bluetoothStateReceiver)
        }
    }

    private fun observeAirplaneToggleState() = lifecycleScope.launch(Dispatchers.IO) {
        getAirplaneToggleStatusUseCase().collect { airplaneToggled ->
            if (airplaneToggled) registerAirplaneReceiver()
            else unregisterBroadcastReceiver(airplaneStateReceiver)
        }
    }

    private fun registerWifiReceiver() {
        unregisterBroadcastReceiver(wifiStateReceiver)
        val intentWifi = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentWifi)
    }

    private fun registerBluetoothRegister() {
        unregisterBroadcastReceiver(bluetoothStateReceiver)
        val intentBluetooth = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothStateReceiver, intentBluetooth)
    }

    private fun registerAirplaneReceiver() {
        unregisterBroadcastReceiver(airplaneStateReceiver)
        val intentAirplane = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneStateReceiver, intentAirplane)
    }

    private fun unregisterBroadcastReceiver(receiver: BroadcastReceiver) {
        try {
            unregisterReceiver(receiver)
        } catch (e: Exception) {
            logw("receiver($receiver) not registered")
        }
    }

    private fun createForegroundNotification() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NOTIFICATION_IMPORTANCE
        )

        channel.setShowBadge(false)
        notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)

        val notification = Notification
            .Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setLargeIcon(Icon.createWithResource(this, R.drawable.ic_launcher_foreground))
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "widget_notification_id"
        private const val NOTIFICATION_CHANNEL_NAME = "widget_notification_name"
        private const val NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_LOW
        private const val NOTIFICATION_ID = 420
    }
}