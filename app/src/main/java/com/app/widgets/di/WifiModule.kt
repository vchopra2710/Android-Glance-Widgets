package com.app.widgets.di

import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WifiModule {
    @Provides
    @Singleton
    fun provideWifiManagerService(
        @ApplicationContext context: Context,
    ): WifiManager {
        return context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
}