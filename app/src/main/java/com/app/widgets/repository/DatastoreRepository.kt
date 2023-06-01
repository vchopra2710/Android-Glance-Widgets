package com.app.widgets.repository

import com.app.widgets.datastore.WidgetDatastore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatastoreRepository @Inject constructor(
    private val widgetDatastore: WidgetDatastore
) : IDatastoreRepository {
    override suspend fun storeWifiStatus(toggled: Boolean) {
        widgetDatastore.storeWifiToggleStatus(toggled = toggled)
    }

    override suspend fun storeBluetoothStatus(toggled: Boolean) {
        widgetDatastore.storeBluetoothToggleStatus(toggled = toggled)
    }

    override suspend fun storeAirplaneStatus(toggled: Boolean) {
        widgetDatastore.storeAirplaneToggleStatus(toggled = toggled)
    }

    override suspend fun storeFlashlightStatus(toggled: Boolean) {
        widgetDatastore.storeFlashLightToggleStatus(toggled = toggled)
    }

    override fun getWifiStatus(): Flow<Boolean> = widgetDatastore.getWifiToggleStatus()

    override fun getBluetoothStatus(): Flow<Boolean> = widgetDatastore.getBluetoothToggleStatus()

    override fun getAirplaneStatus(): Flow<Boolean> = widgetDatastore.getAirplaneToggleStatus()

    override fun getFlashlightStatus(): Flow<Boolean> = widgetDatastore.getFlashlightToggleStatus()
}