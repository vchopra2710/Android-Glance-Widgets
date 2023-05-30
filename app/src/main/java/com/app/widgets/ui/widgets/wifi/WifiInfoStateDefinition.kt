package com.app.widgets.ui.widgets.wifi

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.app.widgets.model.WifiInfo
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object WifiInfoStateDefinition : GlanceStateDefinition<WifiInfo> {

    private const val DATA_STORE_FILENAME = "wifiInfoDataStore"
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, WifiInfoSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<WifiInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object WifiInfoSerializer : Serializer<WifiInfo> {
        override val defaultValue = WifiInfo()

        override suspend fun readFrom(input: InputStream): WifiInfo = try {
            Json.decodeFromString(input.readBytes().decodeToString())
        } catch (exception: Exception) {
            throw CorruptionException("Could not read wifi data: ${exception.message}")
        }

        override suspend fun writeTo(t: WifiInfo, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(WifiInfo.serializer(), t).encodeToByteArray()
                )
            }
        }
    }
}