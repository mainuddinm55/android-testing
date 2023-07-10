package com.example.testingtemplate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.example.testingtemplate.data.local.QuoteDatabase.Companion.DATABASE_VERSION
import com.example.testingtemplate.data.local.converters.TagTypeConverter
import com.example.testingtemplate.data.local.daos.QuoteDao
import com.example.testingtemplate.data.models.Quote


@Database(entities = [Quote::class], version = DATABASE_VERSION)
@TypeConverters(TagTypeConverter::class)
abstract class QuoteDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "db_quote"
        const val DATABASE_VERSION = 1
        val migrations = arrayOf<Migration>()
    }

    abstract fun getQuoteDao(): QuoteDao

}