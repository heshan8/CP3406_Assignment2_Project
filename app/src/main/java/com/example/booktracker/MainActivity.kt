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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                BookStatus.FINISHED -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                BookStatus.READING -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                BookStatus.TO_READ -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f)
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
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = book.status.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = when (book.status) {
                        BookStatus.FINISHED -> MaterialTheme.colorScheme.secondary
                        BookStatus.READING -> MaterialTheme.colorScheme.primary
                        BookStatus.TO_READ -> MaterialTheme.colorScheme.tertiary
                    },
                    fontWeight = FontWeight.Medium
                )

                if (book.status == BookStatus.READING) {
                    Text(
                        text = "${book.progress}",
                        style = MaterialTheme.typography.bodySmall
                        )
                } else if (book.status == BookStatus.FINISHED && book.rating > 0) {
                    Text(
                        text = "★".repeat(book.rating),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}





