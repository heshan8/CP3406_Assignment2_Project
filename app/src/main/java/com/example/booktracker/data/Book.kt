package com.example.booktracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String = "",
    val status: BookStatus,
    val rating: Int = 0,
    val notes: String = "",
    val progress: Int = 0,
    val genre: String = "",
    val dateAdded: Long = System.currentTimeMillis(),
    val dateFinished: Long? = null,
    val coverUrl: String? = null
)
// Using enum to show book status - easier to manage and avoids typos.
enum class BookStatus(val displayName: String) {
    TO_READ("To Read"),
    READING("Reading"),
    FINISHED("Finished")
}

// Extension functions for better usability
fun Book.isFinished() = status == BookStatus.FINISHED
fun Book.isReading() = status == BookStatus.READING
fun Book.progressPercentage() = "$progress%"