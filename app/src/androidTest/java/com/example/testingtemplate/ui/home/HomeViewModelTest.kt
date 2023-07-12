package com.example.testingtemplate.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.data.repositories.FakeQuoteRepository
import com.example.testingtemplate.getOrWaitValue
import com.example.testingtemplate.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(FakeQuoteRepository())
    }

    @Test
    fun test_view_model_quote_as_flow() = runTest {
        val quotes = mutableListOf<Resource<List<Quote>>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.quotes.toList(quotes)
        }
        assertThat(quotes.last()).isInstanceOf(Resource.Success::class.java)
        assertThat((quotes.last() as Resource.Success).data.size).isEqualTo(3)
    }


    @Test
    fun test_view_model_quote_as_livedata() = runTest {
        assertThat(viewModel.quotesLiveData.getOrWaitValue(count = 2)).isInstanceOf(Resource.Success::class.java)
        assertThat((viewModel.quotesLiveData.getOrWaitValue(count = 2) as Resource.Success).data.size).isEqualTo(
            3
        )
    }
}