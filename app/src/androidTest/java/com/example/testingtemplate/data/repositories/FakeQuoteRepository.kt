package com.example.testingtemplate.data.repositories

import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuoteRepository : QuoteRepository {
    override suspend fun getQuotes(): Flow<Resource<List<Quote>>> {
        return flow {
            emit(
                Resource.Success(
                    listOf(
                        Quote(
                            "ABAC",
                            "Md Mainuddin",
                            "mainuddinm55",
                            "This is just dummy content",
                            emptyList(),
                            23,
                            "2023-07-05",
                            "2023-07-05"
                        ), Quote(
                            "ABAB",
                            "Md Mainuddin 2",
                            "mainuddinm55",
                            "This is just is dummy content",
                            emptyList(),
                            23,
                            "2023-07-05",
                            "2023-07-05"
                        ), Quote(
                            "ABAD",
                            "Md Mainuddin3",
                            "mainuddinm55",
                            "This is just also dummy content",
                            emptyList(),
                            23,
                            "2023-07-05",
                            "2023-07-05"
                        )
                    )
                )
            )
        }
    }
}