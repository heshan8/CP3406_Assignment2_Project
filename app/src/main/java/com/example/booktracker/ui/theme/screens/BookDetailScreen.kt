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

    Column(modifier = Modifier
        .padding(WindowInsets.safeDrawing.asPaddingValues())
        .padding(16.dp)) {

        Text(book.title, style = MaterialTheme.typography.headlineSmall)
        Text("Status: ${book.status}", style = MaterialTheme.typography.bodyMedium)
        Text("Rating: ${book.rating}★", style = MaterialTheme.typography.bodySmall)

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