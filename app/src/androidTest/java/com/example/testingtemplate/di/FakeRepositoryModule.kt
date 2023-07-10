package com.example.testingtemplate.di

import com.example.testingtemplate.data.repositories.FakeQuoteRepository
import com.example.testingtemplate.data.repositories.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideQuoteRepository(): QuoteRepository {
        return FakeQuoteRepository()
    }
}