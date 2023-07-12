package com.example.testingtemplate.data.local.converters

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TagTypeConverterTest {
    private lateinit var converter: TagTypeConverter

    @Before
    fun setup() {
        converter = TagTypeConverter()
    }

    @Test
    fun test_convert_tags_from_empty_json() {
        val json = ""
        assertThat(
            try {
                converter.toTags(json)
            } catch (e: Exception) {
                null
            }
        ).isNull()
    }

    @Test
    fun test_convert_tags_from_invalid_json() {
        val json = "name:\"Md Mainuddin\""
        assertThat(
            try {
                converter.toTags(json)
            } catch (e: Exception) {
                null
            }
        ).isNull()
    }

    @Test
    fun test_convert_tags_from_null_json() {
        assertThat(
            try {
                converter.toTags(null)
            } catch (e: Exception) {
                null
            }
        ).isNull()
    }

    @Test
    fun test_convert_tags_from_valid_array() {
        assertThat(converter.fromTags(listOf("Theory"))).isNotEqualTo("null")
    }

    @Test
    fun test_convert_tags_from_empty_array() {
        assertThat(converter.fromTags(listOf())).isEqualTo("[]")
    }

    @Test
    fun test_convert_tags_from_null_array() {
        assertThat(converter.fromTags(null)).isEqualTo("null")
    }
}