package com.example.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.booktracker.ui.theme.BookTrackerTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookRepository
import com.example.booktracker.data.BookStatus
import com.example.booktracker.ui.theme.screens.AddBookScreen
import com.example.booktracker.ui.theme.screens.BookDetailScreen
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.ui.text.input.TextFieldValue

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
                BookListScreen(
                    bookList = repository.books,
                    modifier = Modifier.padding(innerPadding),
                    onBookClick = { selectedBook = it }
                )
            }
        }
    }
}

@Composable
fun BookListScreen(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
) {
    // Group books buy book status
    val readingBooks = bookList.filter { it.status == BookStatus.READING }
    val toReadBooks = bookList.filter { it.status == BookStatus.TO_READ }
    val finishedBooks = bookList.filter { it.status == BookStatus.FINISHED }

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Currently Reading Section
        if (readingBooks.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Currently Reading",
                    count = readingBooks.size,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(readingBooks) { book ->
                BookCard(book = book, onClick = { onBookClick(book) })
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // Books to be read section
        if (toReadBooks.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "To Read",
                    count = toReadBooks.size
                )
            }
            items(toReadBooks) { book ->
                BookCard(book = book, onClick = { onBookClick(book) })
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // Section for finished books
        if (finishedBooks.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Finished",
                    count = finishedBooks.size
                )
            }
            items(finishedBooks) { book ->
                BookCard(book = book, onClick = { onBookClick(book) })
            }
        }

        // Empty state if there are no books
        if (bookList.isEmpty()) {
            item {
                EmptyBookListState(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "$count book${if (count != 1) "s" else ""}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    // Add divider line
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 4.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    )
}

@Composable
fun EmptyBookListState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = ".",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "No books yet!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Tap the + button to add your first book",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = when (book.status) {
                BookStatus.FINISHED -> MaterialTheme.colorScheme.surfaceVariant
                BookStatus.READING -> MaterialTheme.colorScheme.tertiaryContainer
                BookStatus.TO_READ -> MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Book status info
            when (book.status) {
                BookStatus.READING -> {
                    // Show genre if available
                    if (book.genre.isNotEmpty()) {
                        Text(
                            text = book.genre,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                // Reading progress bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = book.status.displayName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    LinearProgressIndicator(
                        progress = { book.progress / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .height(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                }

                BookStatus.FINISHED -> {
                    // Show genre if available
                    if (book.genre.isNotEmpty()) {
                        Text(
                            text = book.genre,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    if (book.rating > 0) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "★".repeat(book.rating),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = "${book.rating}/5 stars",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                BookStatus.TO_READ -> {
                    if (book.genre.isNotEmpty()) {
                        Text(
                            text = book.genre,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}




