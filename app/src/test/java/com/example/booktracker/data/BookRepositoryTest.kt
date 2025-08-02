package com.example.booktracker.data

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookRepositoryTest {

    @Mock
    private lateinit var mockBookDao: BookDao

    private lateinit var repository: BookRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = BookRepository(mockBookDao)
    }

    @Test
    fun addBook_callsDaoInsertBook() = runTest {
        val book = Book(
            title = "Test Book",
            author = "Test Author",
            status = BookStatus.TO_READ
        )

        repository.addBook(book)

        verify(mockBookDao).insertBook(book)
    }

    @Test
    fun updateBook_callsDaoUpdateBook() = runTest {
        val book = Book(
            id = "test-id",
            title = "Updated Book",
            author = "Test Author",
            status = BookStatus.READING
        )

        repository.updateBook(book)

        verify(mockBookDao).updateBook(book)
    }

    @Test
    fun deleteBook_callsDaoDeleteBookById() = runTest {
        val bookId = "test-book-id"

        repository.deleteBook(bookId)

        verify(mockBookDao).deleteBookById(bookId)
    }

    @Test
    fun getAllBooks_callsDaoGetAllBooks() = runTest {
        val expectedBooks = listOf(
            Book(title = "Book 1", author = "Author 1", status = BookStatus.TO_READ),
            Book(title = "Book 2", author = "Author 2", status = BookStatus.READING)
        )
        whenever(mockBookDao.getAllBooks()).thenReturn(flowOf(expectedBooks))

        val result = repository.books

        verify(mockBookDao).getAllBooks()
    }
}