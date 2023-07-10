package com.example.testingtemplate.data.repositories

import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.data.network.QuoteApi
import com.example.testingtemplate.data.network.responses.QuoteResponse
import com.example.testingtemplate.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class QuoteRepositoryImpTest {

    private lateinit var repository: QuoteRepository
    private lateinit var quoteApi: QuoteApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        quoteApi = Retrofit.Builder().baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create()).build().create(QuoteApi::class.java)
        repository = QuoteRepositoryImp(quoteApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_quote_api_with_no_quotes_success_response() {
        runTest {
            val expectedResponse = QuoteResponse(0, 0, 1, 1, 0, emptyList())
            val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(expectedResponse))
            mockWebServer.enqueue(mockResponse)
            val response = repository.getQuotes().toList()
            assertEquals(response.first(), Resource.Loading)
            val data = (response.last() as Resource.Success).data
            assertEquals(data.size, 0)
        }
    }

    @Test
    fun test_quote_api_with_quotes_success_response() {
        runTest {
            val quotes = listOf(
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
            val expectedResponse = QuoteResponse(0, 0, 1, 1, 0, quotes)
            val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(expectedResponse))
            mockWebServer.enqueue(mockResponse)
            val response = repository.getQuotes().toList()
            assertEquals(response.first(), Resource.Loading)
            val data = (response.last() as Resource.Success).data
            assertEquals(data.size, 3)
        }
    }

    @Test
    fun test_quote_api_with_server_error_response() {
        runTest {
            val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_SERVER_ERROR)
            mockWebServer.enqueue(mockResponse)
            val response = repository.getQuotes().toList()
            assertEquals(response.first(), Resource.Loading)
            val msg = (response.last() as Resource.Failed).msg
            assertEquals(msg, "HTTP 500 Server Error")
        }
    }

}