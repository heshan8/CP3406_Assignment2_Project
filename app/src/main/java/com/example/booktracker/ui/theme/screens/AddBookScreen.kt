package com.example.booktracker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookStatus


@Composable
fun AddBookScreen(
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(BookStatus.TO_READ) }
    val rating by remember { mutableIntStateOf(0) }
    var notes by remember { mutableStateOf("") }
    var progress by remember { mutableIntStateOf(0) }

    val isFormValid = title.isNotBlank() && author.isNotBlank()

    Column(
        modifier = Modifier
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp)
    ) {
        Text(
            text = "Add a New Book",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Title
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = title.isBlank()
        )

        // Author
        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            isError = author.isBlank()
        )

        // Genre
        OutlinedTextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Genre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            minLines = 3,
            maxLines = 5
        )

        // Status
        StatusDropdown(
            selected = status,
            onSelected = { status = it },
            modifier = Modifier.padding(vertical = 8.dp)

        // Rating section for finished books
        if (status == BookStatus.FINISHED) {
            Text(
                text = "Rating",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            RatingBar(
                rating = rating,
                onRatingChange = { rating = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Progress Section for currently reading books
        if (status == BookStatus.READING)
            Text(
                text = "Progress: $progress%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Slider(
                value = progress.toFloat(),
                onValueChange = { progress = it.toInt() },
                valueRange = 0f..100f,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        // Buttons
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (isFormValid) {
                        val finalProgress = when (status) {
                            BookStatus.FINISHED -> 100
                            BookStatus.READING -> progress
                            BookStatus.TO_READ -> 0
                        }

                        onSave(
                            Book(
                                title = title,
                                author = author,
                                genre = genre,
                                status = status,
                                notes = notes,
                                rating = if (status == BookStatus.FINISHED) rating else 0,
                                // rating is only kept if the book is marked FINISHED, otherwise it's 0.
                                progress = finalProgress,
                                dateFinished = if (status == BookStatus.FINISHED) System.currentTimeMillis() else null
                                // Set book finish time to current time only if the book in finished otherwise its null
                            )
                        )
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }

@Composable
fun DropdownMenuBox(selected: String, onSelected: (String) -> Unit) {
    val options = listOf("To Read", "Reading", "Finished")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun StatusDropdown(
    selected: BookStatus,
    onSelected: (BookStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = BookStatus.values()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = {expanded = true},
            modifier = modifier.fillMaxWidth()
        ) {
            Text(selected.displayName)
        }
    }
// Placeholder for dropdown UI
// Use DropdownMenu with options from BookStatus.values()
}

// TODO: Implement RatingBar composable for selecting star rating
@Composable
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    // Placeholder for a simple star-based rating bar
    // Loop through 1..maxRating and show stars as Text or Icons
}