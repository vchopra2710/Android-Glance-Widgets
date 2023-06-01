package com.app.widgets.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class WidgetDatastore(private val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

    suspend fun storeWifiToggleStatus(toggled: Boolean) {
        storeValue(toggled = toggled, key = WIFI_KEY)
    }

    suspend fun storeBluetoothToggleStatus(toggled: Boolean) {
        storeValue(toggled = toggled, key = BLUETOOTH_KEY)
    }

    suspend fun storeAirplaneToggleStatus(toggled: Boolean) {
        storeValue(toggled = toggled, key = AIRPLANE_KEY)
    }

    suspend fun storeFlashLightToggleStatus(toggled: Boolean) {
        storeValue(toggled = toggled, key = FLASHLIGHT_KEY)
    }

    fun getWifiToggleStatus(): Flow<Boolean> = getValue(key = WIFI_KEY)

    fun getBluetoothToggleStatus(): Flow<Boolean> = getValue(key = BLUETOOTH_KEY)

    fun getAirplaneToggleStatus(): Flow<Boolean> = getValue(key = AIRPLANE_KEY)

    fun getFlashlightToggleStatus(): Flow<Boolean> = getValue(key = FLASHLIGHT_KEY)

    private suspend fun storeValue(toggled: Boolean, key: String) {
        val booleanKey = booleanPreferencesKey(key)
        context.datastore.edit { it[booleanKey] = toggled }
    }

    private fun getValue(key: String) = context.datastore.data.map {
        val booleanKey = booleanPreferencesKey(key)
        it[booleanKey] ?: false
    }

    companion object {
        private const val DATASTORE_NAME = "widget_datastore"
        private const val WIFI_KEY = "widget_wifi_key"
        private const val BLUETOOTH_KEY = "widget_bluetooth_key"
        private const val AIRPLANE_KEY = "widget_airplane_key"
        private const val FLASHLIGHT_KEY = "widget_flashlight_key"
    }
}