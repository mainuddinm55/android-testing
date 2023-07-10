package com.example.testingtemplate.data.local.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.testingtemplate.data.local.QuoteDatabase
import com.example.testingtemplate.data.models.Quote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: QuoteDatabase
    private lateinit var quoteDao: QuoteDao

    private val quotes = listOf(
        Quote(
            "ABAB",
            "Md Mainuddin 2",
            "mainuddinm55",
            "This is just is dummy content",
            emptyList(),
            23,
            "2023-07-05",
            "2023-07-05"
        ),
        Quote(
            "ABABC",
            "Md Mainuddin 3",
            "mainuddinm553",
            "This is just is3 dummy content",
            emptyList(),
            23,
            "2023-07-05",
            "2023-07-05"
        )
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), QuoteDatabase::class.java
        ).allowMainThreadQueries().build()
        quoteDao = database.getQuoteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_quote_item() {
        runTest {
            quoteDao.insert(quotes)
            val quotes = quoteDao.get().first()
            assertThat(quotes.size).isEqualTo(2)
        }
    }

    @Test
    fun update_quote_item() {
        runTest {
            quoteDao.insert(quotes)
            var dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.first().authorSlug).isEqualTo("mainuddinm55")
            val updatedQuote = quotes.first().copy(authorSlug = "mainuddinm5555")
            quoteDao.update(updatedQuote)
            dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.first().authorSlug).isEqualTo("mainuddinm5555")
        }
    }

    @Test
    fun delete_single_quote_item() {
        runTest {
            quoteDao.insert(quotes)
            var dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.size).isEqualTo(2)
            quoteDao.delete(quotes.first())
            dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.size).isEqualTo(1)
        }
    }

    @Test
    fun delete_all_quotes() {
        runTest {
            quoteDao.insert(quotes)
            var dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.size).isEqualTo(2)
            quoteDao.delete()
            dbQuotes = quoteDao.get().first()
            assertThat(dbQuotes.size).isEqualTo(0)
        }
    }
}