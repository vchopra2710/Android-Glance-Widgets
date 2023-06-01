package com.app.widgets.di

import android.content.Context
import com.app.widgets.datastore.WidgetDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideDatastore(@ApplicationContext context: Context): WidgetDatastore = WidgetDatastore(context = context)
}