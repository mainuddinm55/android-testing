package com.example.testingtemplate.utils

import androidx.annotation.RawRes

data class MockResponseModel(
    val url: String,
    val code: Int,
    @RawRes
    val res: Int
)