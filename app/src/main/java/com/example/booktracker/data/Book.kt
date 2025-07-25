package com.example.booktracker.data

import java.util.UUID

data class Book(
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
enum class BookStatus(val displayName: String) {
    TO_READ("To Read"),
    READING("Reading"),
    FINISHED("Finished")
}

// Extension functions for better usability
fun Book.isFinished() = status == BookStatus.FINISHED
fun Book.isReading() = status == BookStatus.READING
fun Book.progressPercentage() = "$progress%"