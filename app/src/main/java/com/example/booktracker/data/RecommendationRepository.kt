package com.example.booktracker.data

import kotlinx.coroutines.flow.first

class RecommendationRepository(
    private val googleBooksService: GoogleBooksService,
    private val bookRepository: BookRepository
) {

    suspend fun getPersonalizedRecommendations(): List<BookRecommendation> {
        // Get all books from repository
        val allBooks = bookRepository.books.first()
        println("DEBUG: Total books in library: ${allBooks.size}")

        // Find all genres from finished books with 4+ rating
        val favoriteGenres = allBooks
            .filter { book ->
                val isFinished = book.status == BookStatus.FINISHED
                val hasHighRating = book.rating >= 4
                val hasGenre = book.genre.isNotBlank()
                println("DEBUG: Book '${book.title}' - finished: $isFinished, rating: ${book.rating}, genre: '${book.genre}'")
                isFinished && hasHighRating && hasGenre
            }
            .map { it.genre.lowercase().trim() }
            .distinct()

        println("DEBUG: Favorite genres found: $favoriteGenres")

        if (favoriteGenres.isEmpty()) {
            println("DEBUG: No favorite genres found, returning empty list")
            return emptyList()
        }

        // Get recommendations from all top genres
        val allRecommendations = mutableListOf<BookRecommendation>()

        favoriteGenres.forEach { genre ->
            println("DEBUG: Getting recommendations for genre: '$genre'")
            val genreRecommendations = googleBooksService.searchBooksByGenre(genre)
            println("DEBUG: Got ${genreRecommendations.size} recommendations for '$genre'")
            allRecommendations.addAll(genreRecommendations)
        }

        println("DEBUG: Total recommendations before return: ${allRecommendations.size}")
        return allRecommendations
    }
}