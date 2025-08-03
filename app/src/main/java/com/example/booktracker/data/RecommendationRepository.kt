package com.example.booktracker.data

import kotlinx.coroutines.flow.first

class RecommendationRepository(
    private val googleBooksService: GoogleBooksService,
    private val bookRepository: BookRepository
) {

    suspend fun getPersonalizedRecommendations(): List<BookRecommendation> {
        // Get user's favorite genres from their finished books with high ratings
        val favoriteGenres = getFavoriteGenres()

        if (favoriteGenres.isEmpty()) {
            // If no reading history, return popular books
            return emptyList()
        }

        // Get recommendations for the top genre
        return googleBooksService.searchBooksByGenre(favoriteGenres.first())
    }

    private suspend fun getFavoriteGenres(): List<String> {
        // Get all the books from the repository
        val allBooks = bookRepository.books.first()

        // Find all the finished books with rating 4+ and have genres
        val favoriteBooks = allBooks.filter { book ->
            book.status == BookStatus.FINISHED &&
            book.rating >= 4 &&
            book.genre.isNotBlank()
        }

        // Get all the genres and count them
        val genreCounts = favoriteBooks
            .map {it.genre.lowercase().trim()}
            .groupBy { it }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }

        return genreCounts.map { it.first }
    }
}