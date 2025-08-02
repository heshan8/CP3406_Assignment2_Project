package com.example.booktracker

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookRepository
import com.example.booktracker.data.BookDatabase
import com.example.booktracker.data.ThemePreferences
import com.example.booktracker.ui.theme.BookTrackerTheme
import com.example.booktracker.ui.theme.screens.AddBookScreen
import com.example.booktracker.ui.theme.screens.BookDetailScreen
import com.example.booktracker.ui.theme.components.BookListScreen
import com.example.booktracker.ui.theme.components.SearchTopBar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTrackerApp()
        }
    }
}


@Composable
fun BookTrackerApp() {
    //Dark mode state gets saved after restating the app
    val context = androidx.compose.ui.platform.LocalContext.current
    val themePrefs = remember { ThemePreferences(context) }

    var isDarkMode by remember {
        mutableStateOf(themePrefs.isDarkMode())
    }

    // Update status bar appearance when theme changes
    LaunchedEffect(isDarkMode) {
        val activity = context as ComponentActivity
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
            isAppearanceLightStatusBars = !isDarkMode
        }
    }

    //Wrap inside dark theme
    BookTrackerTheme(darkTheme = isDarkMode) {
        //Initialize database and book repository
        val repository = remember {
            val database = BookDatabase.getDatabase(context)
            BookRepository(database.bookDao())
        }
        var showAddScreen by remember { mutableStateOf(false) }
        var selectedBook by remember { mutableStateOf<Book?>(null) }
        var isSearching by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }

        // Collect books from flow (this replaces repository.books list)
        val books by repository.books.collectAsState(initial = emptyList())

        //Filtering the books based on search terms
        val filteredBooks = remember(books, searchQuery) {
            if (searchQuery.isEmpty()) {
                books
            } else {
                books.filter { book ->
                    book.title.contains(searchQuery.trim(), ignoreCase = true) ||
                            book.author.contains(searchQuery.trim(), ignoreCase = true) ||
                            book.genre.contains(searchQuery.trim(), ignoreCase = true)
                }
            }
        }

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
                        },
                        isDarkMode = isDarkMode,
                        onDarkModeToggle = {
                            isDarkMode = !isDarkMode
                            themePrefs.setDarkMode(isDarkMode)
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
                            //Wrap call in coroutine
                            (context as ComponentActivity).lifecycleScope.launch {
                                repository.addBook(newBook)
                            }
                            showAddScreen = false
                        },
                        onCancel = { showAddScreen = false }
                    )
                }

                selectedBook != null -> {
                    BookDetailScreen(
                        book = selectedBook!!,
                        onSave = { updatedBook ->
                            //Wrap call in coroutine
                            (context as ComponentActivity).lifecycleScope.launch {
                                repository.updateBook(updatedBook)
                            }
                            selectedBook = null
                        },
                        onDelete = { bookId ->
                            //Wrap call in coroutine
                            (context as ComponentActivity).lifecycleScope.launch {
                                repository.deleteBook(bookId)
                            }
                            selectedBook = null
                        },
                        onCancel = { selectedBook = null }
                    )
                }

                else -> {
                    BookListScreen(
                        bookList = filteredBooks,
                        modifier = Modifier.padding(innerPadding),
                        onBookClick = { selectedBook = it }
                    )
                }
            }
        }
    }
}