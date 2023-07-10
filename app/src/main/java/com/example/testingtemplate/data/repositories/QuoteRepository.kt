package com.example.testingtemplate.data.repositories

import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun getQuotes(): Flow<Resource<List<Quote>>>
}