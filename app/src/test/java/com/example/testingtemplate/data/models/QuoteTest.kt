package com.example.testingtemplate.data.models

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat

class QuoteTest {
    private lateinit var quote: Quote

    @Before
    fun setup() {
        quote = Quote(
            "ABAB",
            "Md Mainuddin 2",
            "mainuddinm55",
            "This is just is dummy content",
            emptyList(),
            23,
            "2023-07-05",
            "2023-07-05"
        )
    }

    @Test
    fun test_valid_quote() {
        assertThat(quote._id).isNotEmpty()
        assertThat(quote.author).isNotEmpty()
        assertThat(quote.authorSlug).isNotEmpty()
        assertThat(quote.content).isNotEmpty()
        assertThat(quote.length).isNotEqualTo(0)
        assertThat(quote.dateAdded).isNotEmpty()
        assertThat(quote.dateModified).isNotEmpty()
    }

    @Test
    fun test_date_is_valid_format() {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        //Strictly check format
        dateFormat.isLenient = false
        assertThat(
            try {
                dateFormat.parse(quote.dateAdded)
            } catch (e: Exception) {
                null
            }
        ).isNotNull()
        assertThat(
            try {
                dateFormat.parse(quote.dateModified)
            } catch (e: Exception) {
                null
            }
        ).isNotNull()
    }

    @Test
    fun test_date_is_invalid_format() {
        val dateFormat = SimpleDateFormat("dd-mm-yyyy")
        //Strictly check format
        dateFormat.isLenient = false
        assertThat(
            try {
                dateFormat.parse(quote.dateAdded)
            } catch (e: Exception) {
                null
            }
        ).isNull()
        assertThat(
            try {
                dateFormat.parse(quote.dateModified)
            } catch (e: Exception) {
                null
            }
        ).isNull()
    }


}