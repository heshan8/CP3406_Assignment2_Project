package com.example.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookRepository
import com.example.booktracker.ui.theme.BookTrackerTheme
import com.example.booktracker.ui.theme.screens.AddBookScreen
import com.example.booktracker.ui.theme.screens.BookDetailScreen
import com.example.booktracker.ui.theme.components.BookListScreen
import com.example.booktracker.ui.theme.components.SearchTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTrackerTheme {
                BookTrackerApp()
            }
        }
    }
}

@Composable
fun BookTrackerApp() {
    val repository = remember { BookRepository() }
    var showAddScreen by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!showAddScreen && selectedBook == null) {
                SearchTopBar(
                    isSearching = isSearching,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    onSearchToggle = {
                        isSearching = !isSearching
                        if (!isSearching) searchQuery = ""
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddScreen = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { innerPadding ->
        when {
            showAddScreen -> {
                AddBookScreen(
                    onSave = { newBook ->
                        repository.addBook(newBook)
                        showAddScreen = false
                    },
                    onCancel = { showAddScreen = false }
                )
            }

            selectedBook != null -> {
                BookDetailScreen(
                    book = selectedBook!!,
                    onSave = { updatedBook ->
                        repository.updateBook(updatedBook)
                        selectedBook = null
                    },
                    onDelete = { bookId ->
                        repository.deleteBook(bookId)
                        selectedBook = null
                    },
                    onCancel = { selectedBook = null }
                )
            }

            else -> {
                // Filter books based on search query
                val filteredBooks = if (searchQuery.isEmpty()) {
                    repository.books
                } else {
                    repository.books.filter { book ->
                        book.title.contains(searchQuery, ignoreCase = true) ||
                                book.author.contains(searchQuery, ignoreCase = true) ||
                                book.genre.contains(searchQuery, ignoreCase = true)
                    }
                }

                BookListScreen(
                    bookList = filteredBooks,
                    modifier = Modifier.padding(innerPadding),
                    onBookClick = { selectedBook = it }
                )
            }
        }
    }
}