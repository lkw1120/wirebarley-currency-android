package com.lkw1120.currency.di

import android.content.Context
import com.lkw1120.currency.datasource.local.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        @ApplicationContext appContext: Context
    ): LocalDataSource {
        return LocalDataSource.apply {
            setAppDatabase(appContext)
        }
    }

}