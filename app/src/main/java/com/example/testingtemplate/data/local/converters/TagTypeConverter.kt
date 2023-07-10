package com.example.testingtemplate.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TagTypeConverter {
    @TypeConverter
    fun toTags(json: String?): List<String>? {
        return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromTags(tags: List<String>?): String? {
        return Gson().toJson(tags)
    }
}