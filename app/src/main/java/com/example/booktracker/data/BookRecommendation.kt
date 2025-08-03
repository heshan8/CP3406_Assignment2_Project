package com.example.booktracker.data


data class BookRecommendation(
    val title: String,
    val author: String,
    val description: String = "",
    val genres: List<String> = emptyList()
)

// Extension function to convert BookRecommendation to Book
fun BookRecommendation.toBook(): Book {
    return Book(
        title = this.title,
        author = this.author,
        genre = this.genres.firstOrNull() ?: "",
        status = BookStatus.TO_READ,
        notes = this.description.ifBlank { "" },
    )
}