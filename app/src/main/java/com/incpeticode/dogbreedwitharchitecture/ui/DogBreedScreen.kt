package com.incpeticode.dogbreedwitharchitecture.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// Composable function to display the dog breed screen
@Composable
fun DogBreedScreen(
    breeds: Map<String, List<String>>?,
    isLoading: Boolean,
    onBreedClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator() // Show loading indicator
        } else {
            breeds?.let { breeds ->
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(breeds.keys.toList()) { breed ->
                        BreedItem(
                            breed = breed,
                            onClick = { onBreedClick(breed) }) // Display breed items
                    }
                }
            }
        }
    }
}
