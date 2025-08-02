package com.example.booktracker.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookStatus

@Composable
fun BookListScreen(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
) {
    // Group books by book status
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
                    title = "Finished Reading",
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