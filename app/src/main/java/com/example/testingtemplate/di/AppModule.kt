package com.example.testingtemplate.di

import android.content.Context
import androidx.room.Room
import com.example.testingtemplate.BuildConfig
import com.example.testingtemplate.utils.MockResponseModel
import com.example.testingtemplate.R
import com.example.testingtemplate.data.local.QuoteDatabase
import com.example.testingtemplate.data.local.QuoteDatabase.Companion.DATABASE_NAME
import com.example.testingtemplate.data.network.QuoteApi
import com.example.testingtemplate.utils.readFileResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMockInfo(): List<MockResponseModel> {
        return listOf(
            MockResponseModel(
                url = "https://quotable.io/quotes",
                code = HttpURLConnection.HTTP_OK,
                res = R.raw.quotes
            )
        )
    }

    @Provides
    @Singleton
    fun provideMockResponseInterceptor(
        @ApplicationContext context: Context, mocks: List<MockResponseModel>
    ): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            var code = HttpURLConnection.HTTP_OK
            if (BuildConfig.DEBUG) {
                val response = mocks.find { it.url == originalRequest.url.toString() }?.let {
                    code = it.code
                    return@let readFileResource(
                        context.resources.openRawResource(it.res)
                    )
                }
                response?.let {
                    return@Interceptor chain.proceed(originalRequest).newBuilder().code(code)
                        .protocol(Protocol.HTTP_2)
                        .body(it.toResponseBody("application/json".toMediaTypeOrNull()))
                        .addHeader("content-type", "application/json").build()
                } ?: return@Interceptor chain.proceed(originalRequest)

            }
            return@Interceptor chain.proceed(originalRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(mockInterceptor: Interceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(logger)
            addInterceptor(mockInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideQuoteApi(client: OkHttpClient): QuoteApi {
        return Retrofit.Builder().baseUrl(QuoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            .create(QuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(context, QuoteDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().addMigrations(*QuoteDatabase.migrations).build()
    }
}