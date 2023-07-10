package com.example.testingtemplate.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.testingtemplate.data.local.converters.TagTypeConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "quotes")
@Parcelize
data class Quote(
    @ColumnInfo(name = "_id") @PrimaryKey val _id: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "author_slug") val authorSlug: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "tags") val tags: List<String>?,
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "data_added") val dateAdded: String,
    @ColumnInfo(name = "date_modified") val dateModified: String
):Parcelable