package com.example.testingtemplate.data.repositories

import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.data.network.QuoteApi
import com.example.testingtemplate.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuoteRepositoryImp @Inject constructor(
    private val quoteApi: QuoteApi
) : QuoteRepository {

    override suspend fun getQuotes(): Flow<Resource<List<Quote>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = quoteApi.getQuotes()
                emit(Resource.Success(response.results))
            } catch (e: Exception) {
                emit(Resource.Failed(e.message))
            }
        }
    }

}