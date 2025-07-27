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
        modifier = Modifier.padding(WindowInsets.safeDrawing.asPaddingValues()).padding(16.dp)    ) {
        Text("Add a New Book", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        DropdownMenuBox(selected = status, onSelected = { status = it })

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(Book(title, status, rating, notes))
                }
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