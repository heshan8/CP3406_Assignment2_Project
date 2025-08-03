package com.example.booktracker.data


import kotlinx.coroutines.delay

class GoogleBooksService {

    suspend fun searchBooksByGenre(genre: String): List<BookRecommendation> {
        // Using mock data for now
        delay(1000) // This is to simulate delays from network

        return when (genre.lowercase()) {
            "fantasy" -> listOf(
                BookRecommendation(
                    title = "The Name of the Wind",
                    author = "Patrick Rothfuss",
                    description = "The riveting first-person narrative of a young man who grows to be the most notorious magician his world has ever seen.",
                    genres = listOf("Fantasy")
                ),
                BookRecommendation(
                    title = "The Way of Kings",
                    author = "Brandon Sanderson",
                    description = "Epic fantasy novel set on the storm-ravaged world of Roshar.",
                    genres = listOf("Fantasy")
                )
            )
            "sci-fi", "science fiction" -> listOf(
                BookRecommendation(
                    title = "Dune",
                    author = "Frank Herbert",
                    description = "Set in the distant future amidst a feudal interstellar society.",
                    genres = listOf("Science Fiction")
                )
            )
            else -> emptyList()
        }
    }
}