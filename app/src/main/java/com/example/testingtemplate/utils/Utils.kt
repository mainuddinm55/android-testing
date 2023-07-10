package com.example.testingtemplate.utils

import java.io.InputStream
import java.io.InputStreamReader

fun readFileResource(inputStream: InputStream): String {
    val builder = StringBuilder()
    val reader = InputStreamReader(inputStream, "UTF-8")
    reader.readLines().forEach {
        builder.append(it)
    }

    return builder.toString()
}
