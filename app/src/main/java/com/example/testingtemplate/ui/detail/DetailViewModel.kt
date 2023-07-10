package com.example.testingtemplate.ui.detail

import com.example.testingtemplate.data.repositories.QuoteRepository
import com.example.testingtemplate.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    repository: QuoteRepository
) : BaseViewModel() {

}