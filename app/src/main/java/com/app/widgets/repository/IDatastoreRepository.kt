package com.app.widgets.repository

import kotlinx.coroutines.flow.Flow

interface IDatastoreRepository {
    suspend fun storeWifiStatus(toggled: Boolean)
    suspend fun storeBluetoothStatus(toggled: Boolean)
    suspend fun storeAirplaneStatus(toggled: Boolean)
    suspend fun storeFlashlightStatus(toggled: Boolean)
    fun getWifiStatus(): Flow<Boolean>
    fun getBluetoothStatus(): Flow<Boolean>
    fun getAirplaneStatus(): Flow<Boolean>
    fun getFlashlightStatus(): Flow<Boolean>
}