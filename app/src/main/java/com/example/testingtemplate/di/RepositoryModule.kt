package com.example.testingtemplate.di

import com.example.testingtemplate.data.network.QuoteApi
import com.example.testingtemplate.data.repositories.QuoteRepository
import com.example.testingtemplate.data.repositories.QuoteRepositoryImp
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
    fun provideQuoteRepository(quoteApi: QuoteApi): QuoteRepository {
        return QuoteRepositoryImp(quoteApi)
    }
}