package com.incpeticode.dogbreedwitharchitecture.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogIntent
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogViewModel
import com.incpeticode.dogbreedwitharchitecture.ui.viewmodel.DogViewState

// Composable function to display the screen with the list of dog breeds
@Composable
fun DogBreedScreen(viewModel: DogViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val viewState by viewModel.viewState.collectAsState() // Observe the ViewState from the ViewModel

    Column(
        modifier = modifier.padding(16.dp), // Add padding around the column
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (viewState) {
            is DogViewState.Idle -> { // Initial state before any data is loaded
                LaunchedEffect(Unit) {
                    viewModel.intentChannel.send(DogIntent.FetchAllBreeds) // Send intent to fetch all breeds
                }
                Text("Initializing...") // Show initializing text
            }
            is DogViewState.Loading -> CircularProgressIndicator() // Show loading indicator
            is DogViewState.BreedList -> { // State when the breed list is loaded
                val breeds = (viewState as DogViewState.BreedList).breeds // Get the breeds from the ViewState
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Make the list take available space
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(breeds.keys.toList()) { breed ->
                        BreedItem(
                            breed = breed,
                            onClick = {
                                viewModel.intentChannel.trySend(DogIntent.FetchRandomImageByBreed(breed)) // Send intent to fetch random image for the breed
                            })
                    }
                }
            }
            is DogViewState.Error -> Text("Error: ${(viewState as DogViewState.Error).message}") // Show error message
            else -> Unit // Do nothing for other states
        }
    }
}
