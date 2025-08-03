package com.example.booktracker.data

import com.example.booktracker.data.api.RetrofitInstance
import kotlinx.coroutines.delay

class GoogleBooksService {

    suspend fun searchBooksByGenre(genre: String): List<BookRecommendation> {
        return try {
            val response = RetrofitInstance.googleBooksApi.searchBooks("subject:$genre")

            val results = response.items?.mapNotNull { bookItem ->
                val apiCategories = bookItem.volumeInfo.categories ?: emptyList()

                // Be more lenient with filtering - if the book comes from a genre search then it's probably relevant
                val isRelevant = if (apiCategories.isEmpty()) {
                    // If no categories, assume it's relevant since it came from genre search
                    true
                } else {
                    // Check if any category is somewhat related
                    apiCategories.any { category ->
                        isGenreMatch(genre, category)
                    }
                }

                if (isRelevant) {
                    BookRecommendation(
                        title = bookItem.volumeInfo.title,
                        author = bookItem.volumeInfo.authors?.firstOrNull() ?: "Unknown Author",
                        description = bookItem.volumeInfo.description ?: "",
                        genres = listOf(normalizeGenre(genre))
                    )
                } else {
                    null
                }
            }?.take(5) ?: emptyList()

            results

        } catch (e: Exception) {
            // Fallback to mock data on error
            searchBooksByGenreMock(genre)
        }
    }

    private fun isGenreMatch(userGenre: String, apiCategory: String): Boolean {
        val normalizedUserGenre = userGenre.lowercase().trim()
        val normalizedApiCategory = apiCategory.lowercase().trim()

        if (normalizedApiCategory.contains(normalizedUserGenre) ||
            normalizedUserGenre.contains(normalizedApiCategory)) {
            return true
        }

        // Using common genre synonyms
        return when (normalizedUserGenre) {
            "mystery" -> normalizedApiCategory.contains("fiction") ||
                    normalizedApiCategory.contains("crime") ||
                    normalizedApiCategory.contains("detective") ||
                    normalizedApiCategory.contains("thriller") ||
                    normalizedApiCategory.contains("suspense")
            "romance" -> normalizedApiCategory.contains("fiction") ||
                    normalizedApiCategory.contains("love") ||
                    normalizedApiCategory.contains("relationship")
            "fantasy" -> normalizedApiCategory.contains("fiction") ||
                    normalizedApiCategory.contains("magic") ||
                    normalizedApiCategory.contains("adventure")
            "science fiction" -> normalizedApiCategory.contains("fiction") ||
                    normalizedApiCategory.contains("sci-fi") ||
                    normalizedApiCategory.contains("science")
            else -> {
                // Being very lenient for unknown categories
                normalizedApiCategory.contains("fiction") ||
                        normalizedUserGenre.split(" ").any { word ->
                            word.length > 2 && normalizedApiCategory.contains(word)
                        }
            }
        }
    }

    private fun normalizeGenre(genre: String): String {
        return when (genre.lowercase().trim()) {
            "sci-fi" -> "Science Fiction"
            "non-fiction" -> "Non-Fiction"
            else -> genre.lowercase().split(" ").joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
        }
    }

    private suspend fun searchBooksByGenreMock(genre: String): List<BookRecommendation> {
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
            "mystery" -> listOf(
                BookRecommendation(
                    title = "Gone Girl",
                    author = "Gillian Flynn",
                    description = "A psychological thriller about a marriage gone terribly wrong.",
                    genres = listOf("Mystery")
                )
            )
            "romance" -> listOf(
                BookRecommendation(
                    title = "Pride and Prejudice",
                    author = "Jane Austen",
                    description = "A classic romance novel about Elizabeth Bennet and Mr. Darcy.",
                    genres = listOf("Romance")
                )
            )
            "science fiction", "sci-fi" -> listOf(
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