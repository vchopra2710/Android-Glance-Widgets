package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.app.widgets.model.ConnectivityInfo
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object ConnectivityStateDefinition : GlanceStateDefinition<ConnectivityInfo> {

    private const val DATA_STORE_FILENAME = "connectivityDataStore"
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, ConnectivitySerializer)

    override suspend fun getDataStore(
        context: Context,
        fileKey: String
    ): DataStore<ConnectivityInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object ConnectivitySerializer : Serializer<ConnectivityInfo> {
        override val defaultValue: ConnectivityInfo
            get() = ConnectivityInfo()

        override suspend fun readFrom(input: InputStream): ConnectivityInfo = try {
            Json.decodeFromString(input.readBytes().decodeToString())
        } catch (exception: Exception) {
            throw CorruptionException("Could not read connectivity data: ${exception.message}")
        }

        override suspend fun writeTo(t: ConnectivityInfo, output: OutputStream) {
            output.use {
                it.write(Json.encodeToString(ConnectivityInfo.serializer(), t).encodeToByteArray())
            }
        }

    }
}