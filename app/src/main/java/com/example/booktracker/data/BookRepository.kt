package com.example.booktracker.data

import android.adservices.adid.AdId
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    // Change mutableStateListOf to Flow from database
    val books: Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(updatedBook: Book) {
        bookDao.updateBook(updatedBook)
    }

    suspend fun deleteBook(bookId: String) {
        bookDao.deleteBookById(bookId)
    }

}

