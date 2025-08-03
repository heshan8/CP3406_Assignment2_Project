package com.example.booktracker.data

import com.example.booktracker.data.api.RetrofitInstance
import kotlinx.coroutines.delay

class GoogleBooksService {

    suspend fun searchBooksByGenre(genre: String): List<BookRecommendation> {
        return try {
            println("DEBUG: Searching for genre: '$genre'")
            val response = RetrofitInstance.googleBooksApi.searchBooks("subject:$genre")
            println("DEBUG: API returned ${response.totalItems} total items, ${response.items?.size ?: 0} items in response")

            val results = response.items?.map { bookItem ->
                println("DEBUG: Book: '${bookItem.volumeInfo.title}' by ${bookItem.volumeInfo.authors?.firstOrNull() ?: "Unknown"}")
                BookRecommendation(
                    title = bookItem.volumeInfo.title,
                    author = bookItem.volumeInfo.authors?.firstOrNull() ?: "Unknown Author",
                    description = bookItem.volumeInfo.description ?: "",
                    genres = bookItem.volumeInfo.categories ?: emptyList()
                )
            } ?: emptyList()

            println("DEBUG: Returning ${results.size} recommendations")
            results

        } catch (e: Exception) {
            println("DEBUG: API error: ${e.message}")
            // Fallback to mock data on error
            searchBooksByGenreMock(genre)
        }
    }

    private suspend fun searchBooksByGenreMock(genre: String): List<BookRecommendation> {
        println("DEBUG: Using mock data for genre: '$genre'")
        delay(1000)

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