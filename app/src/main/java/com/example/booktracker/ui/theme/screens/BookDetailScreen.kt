package com.example.booktracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookStatus

@Composable
fun BookDetailScreen(
    book: Book,
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var notes by remember { mutableStateOf(book.notes) }
    var progress by remember { mutableStateOf(book.progress) }
    var status by remember { mutableStateOf(book.status) }
    var rating by remember { mutableStateOf(book.rating) }

    Column(
        modifier = Modifier
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp)
    ) {

        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "by ${book.author}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        if ( book.genre.isNotEmpty()) {
            Text(
                text = "Genre: ${book.genre}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Book Satus Dropdown
        Text(
            text = "Status",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        StatusDropdown(
            selected = status,
            onSelected = { status = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Rating section for finished books
        if (status == BookStatus.FINISHED) {
            Text(
                text = "Rating",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            RatingBar(
                rating = rating,
                onRatingChange = { rating = it},
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text("Progress: $progress%", style = MaterialTheme.typography.bodyMedium)

        Slider(
            value = progress.toFloat(),
            onValueChange = { progress = it.toInt() },
            valueRange = 0f..100f
        )

        Spacer(Modifier.height(24.dp))

        Row {
            Button(onClick = {
                onSave(book.copy(notes = notes, progress = progress))
            }) {
                Text("Save")
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}