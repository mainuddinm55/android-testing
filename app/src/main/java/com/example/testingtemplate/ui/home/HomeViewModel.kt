package com.example.testingtemplate.ui.home

import androidx.lifecycle.viewModelScope
import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.data.repositories.QuoteRepository
import com.example.testingtemplate.ui.base.BaseViewModel
import com.example.testingtemplate.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuoteRepository
) : BaseViewModel() {
    private val _quotes = MutableStateFlow<Resource<List<Quote>>>(Resource.Loading)
    val quotes: StateFlow<Resource<List<Quote>>> = _quotes

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            repository.getQuotes().catch { e ->
                _quotes.value = Resource.Failed(e.message)
            }.collect {
                _quotes.value = it
            }
        }
    }

}