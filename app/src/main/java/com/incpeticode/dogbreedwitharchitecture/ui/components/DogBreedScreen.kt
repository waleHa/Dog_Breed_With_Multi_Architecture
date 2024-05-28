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

@Composable
fun DogBreedScreen(viewModel: DogViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val viewState by viewModel.viewState.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (viewState) {
            is DogViewState.Idle -> {
                LaunchedEffect(Unit) {
                    viewModel.intentChannel.send(DogIntent.FetchAllBreeds)
                }
                Text("Initializing...")
            }
            is DogViewState.Loading -> CircularProgressIndicator()
            is DogViewState.BreedList -> {
                val breeds = (viewState as DogViewState.BreedList).breeds
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(breeds.keys.toList()) { breed ->
                        BreedItem(
                            breed = breed,
                            onClick = {
                                viewModel.intentChannel.trySend(DogIntent.FetchRandomImageByBreed(breed))
                            })
                    }
                }
            }
            is DogViewState.Error -> Text("Error: ${(viewState as DogViewState.Error).message}")
            else -> Unit
        }
    }
}