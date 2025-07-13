package com.example.booktracker.data

import java.util.UUID

data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String = "",
    val status: String = "",
    val rating: Int = 0,
    val notes: String = "",
    val progress: Int = 0,
    val genre: String = "",
    val dateAdded: Long = System.currentTimeMillis(),
    val dateFinished: Long? = null,
    val coverUrl: String? = null
)