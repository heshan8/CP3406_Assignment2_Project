package com.example.booktracker.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.booktracker.data.RecommendationRepository
import androidx.compose.runtime.*
import com.example.booktracker.data.BookRecommendation

@Composable
fun DiscoverScreen(
    recommendationRepository: RecommendationRepository
) {
    var recommendations by remember { mutableStateOf<List<BookRecommendation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🔎", style = MaterialTheme.typography.displayLarge)
        Text(text = "Discover Books", style = MaterialTheme.typography.headlineSmall)
    }
}