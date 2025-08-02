package com.example.booktracker.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DiscoverScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🔎", style = MaterialTheme.typography.displayLarge)
        Text(text = "Discover Books", style = MaterialTheme.typography.headlineSmall)
    }
}