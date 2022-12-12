package com.lkw1120.currency.di

import com.lkw1120.currency.datasource.remote.RemoteDataSource
import com.lkw1120.currency.datasource.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(

    ): RemoteDataSource {
        return RemoteDataSourceImpl()
    }

}