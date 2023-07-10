package com.example.testingtemplate.data.network.responses

import com.example.testingtemplate.data.models.Quote

data class QuoteResponse(
    val count: Int,
    val totalCount: Int,
    val page: Int,
    val totalPage: Int,
    val lastItemIndex: Int,
    val results: List<Quote>
)