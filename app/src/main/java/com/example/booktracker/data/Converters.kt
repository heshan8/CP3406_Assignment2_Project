package com.example.booktracker.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBookStatus(status: BookStatus): String = status.name

    @TypeConverter
    fun toBookStatus(status: String): BookStatus = BookStatus.valueOf(status)
}