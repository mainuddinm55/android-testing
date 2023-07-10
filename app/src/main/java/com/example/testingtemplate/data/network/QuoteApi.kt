package com.example.testingtemplate.data.network

import com.example.testingtemplate.data.network.responses.QuoteResponse
import retrofit2.http.GET

interface QuoteApi {
    companion object {
        const val BASE_URL = "https://quotable.io/"
    }

    @GET("quotes")
    suspend fun getQuotes(): QuoteResponse
}