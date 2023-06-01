package com.app.widgets.di

import com.app.widgets.datastore.WidgetDatastore
import com.app.widgets.repository.DatastoreRepository
import com.app.widgets.repository.IDatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(
        widgetDatastore: WidgetDatastore,
    ): IDatastoreRepository = DatastoreRepository(
        widgetDatastore = widgetDatastore,
    )
}