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
import com.example.booktracker.ui.theme.screens.AddBookScreen
import com.example.booktracker.ui.theme.screens.BookDetailScreen


data class Book(
    val title: String,
    val status: String,
    val rating: Int,
    val notes: String,
    val progress: Int = 0
)

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
                    val bookList = remember { mutableStateListOf(
                        Book("Atomic Habits", "Reading", 5, "Taking notes", 30),
                        Book("1984", "Finished", 4, "Chilling ending", 100),
                        Book("The Hobbit", "To Read", 0, "", 0),
                        Book("Deep Work", "Reading", 3, "Good focus tips", 45),
                        Book("Dune", "To Read", 0, "", 0),
                        Book("The Midnight Library", "Finished", 4, "Really makes you reflect", 100),

                    ) }



                    when {
                        showAddScreen -> {
                            AddBookScreen(
                                onSave = { newBook ->
                                    bookList.add(newBook)
                                    showAddScreen = false
                                },
                                onCancel = { showAddScreen = false }
                            )
                        }

                        selectedBook != null -> {
                            BookDetailScreen(
                                book = selectedBook!!,
                                onSave = { updatedBook ->
                                    val index = bookList.indexOfFirst { it.title == selectedBook!!.title }
                                    if (index != -1) bookList[index] = updatedBook
                                    selectedBook = null
                                },
                                onCancel = { selectedBook = null }
                            )
                        }

                        else -> {
                            BookListScreen(
                                bookList = bookList,
                                modifier = Modifier.padding(innerPadding),
                                onAddClick = { showAddScreen = true },
                                onBookClick = { selectedBook = it }
                            )
                        }
                    }


                }
                    }


                }
            }
        }


    @Composable
    fun BookListScreen(bookList: List<Book>,
                       modifier: Modifier = Modifier,
                       onAddClick: () -> Unit,
                       onBookClick: (Book) -> Unit ) {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(bookList) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onBookClick(book) },

                    colors = CardDefaults.cardColors(
                        containerColor = when (book.status) {
                            "Finished" -> MaterialTheme.colorScheme.secondaryContainer
                            "Reading" -> MaterialTheme.colorScheme.primaryContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(book.title, style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${book.status}")
                        Text("Rating: ${book.rating}★")
                    }
                }
            }
        }
    }




