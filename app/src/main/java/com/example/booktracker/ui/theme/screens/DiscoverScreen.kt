package com.example.booktracker.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.booktracker.data.RecommendationRepository
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.BookRecommendation

@Composable
fun DiscoverScreen(
    recommendationRepository: RecommendationRepository
) {
    var recommendations by remember { mutableStateOf<List<BookRecommendation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Load recommendations once the screen is opened
    LaunchedEffect(Unit) {
        recommendations = recommendationRepository.getPersonalizedRecommendations()
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Discover Books", style = MaterialTheme.typography.headlineSmall)
    }
}