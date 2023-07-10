package com.example.testingtemplate.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testingtemplate.data.models.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert
    fun insert(quote: List<Quote>)

    @Update
    fun update(quote: Quote)

    @Delete
    fun delete(quote: Quote)

    @Query("DELETE FROM quotes")
    fun delete()

    @Query("SELECT * FROM quotes")
    fun get(): Flow<List<Quote>>
}