package com.example.booktracker.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.booktracker.data.RecommendationRepository
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
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else if (recommendations.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "📖", style = MaterialTheme.typography.displayLarge)
                Text(
                    text = "No Recommendations Yet",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Add some books to your library and rate them first to get personalized recommendations!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Text(text = "Discover Books", style = MaterialTheme.typography.headlineSmall)
        }
    }
}