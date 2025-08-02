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
    fun testPlaceholder() {
        assertTrue(true)
    }
}