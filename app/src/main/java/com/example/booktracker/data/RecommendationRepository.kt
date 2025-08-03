package com.example.booktracker.data

import kotlinx.coroutines.flow.first

class RecommendationRepository(
    private val googleBooksService: GoogleBooksService,
    private val bookRepository: BookRepository
) {

    suspend fun getPersonalizedRecommendations(): List<BookRecommendation> {
        // Get all books from repository
        val allBooks = bookRepository.books.first()

        // Find all genres from finished books with 4+ rating
        val favoriteGenres = allBooks
            .filter { book ->
                book.status == BookStatus.FINISHED &&
                book.rating >= 4 &&
                book.genre.isNotBlank()
            }
            .map { it.genre.lowercase().trim() }
            .distinct() // This will remove duplicate books

        if (favoriteGenres.isEmpty()) {
            return emptyList()
        }


        // Get recommendations from all top genres
        val allRecommendations = mutableListOf<BookRecommendation>()

        favoriteGenres.forEach { genre ->
            val genreRecommendations = googleBooksService.searchBooksByGenre(genre)
            allRecommendations.addAll(genreRecommendations)
        }

        return allRecommendations
    }
}