package com.example.booktracker.data


data class BookRecommendation(
    val title: String,
    val author: String,
    val description: String = "",
    val genres: List<String> = emptyList()
)
