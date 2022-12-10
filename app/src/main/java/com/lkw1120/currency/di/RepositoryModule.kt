package com.lkw1120.currency.di

import com.lkw1120.currency.datasource.local.LocalDataSource
import com.lkw1120.currency.datasource.remote.RemoteDataSource
import com.lkw1120.currency.repository.CurrencyRepository
import com.lkw1120.currency.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStationRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            localDataSource,
            remoteDataSource
        )
    }

}