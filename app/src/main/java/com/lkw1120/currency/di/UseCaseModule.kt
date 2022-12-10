package com.lkw1120.currency.di

import com.lkw1120.currency.repository.CurrencyRepository
import com.lkw1120.currency.usecase.CurrencyUseCase
import com.lkw1120.currency.usecase.CurrencyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideStationUseCase (
        currencyRepository: CurrencyRepository
    ): CurrencyUseCase {
        return CurrencyUseCaseImpl(currencyRepository)
    }

}