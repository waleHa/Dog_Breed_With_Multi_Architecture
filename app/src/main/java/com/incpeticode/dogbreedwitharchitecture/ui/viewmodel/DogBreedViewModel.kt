package com.incpeticode.dogbreedwitharchitecture.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incpeticode.dogbreedwitharchitecture.data.repository.DogRepository
import com.incpeticode.dogbreedwitharchitecture.data.util.Constants
import com.incpeticode.dogbreedwitharchitecture.domain.model.BreedListModel
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel class to manage the data and state for the DogView
@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {

    val intentChannel = Channel<DogIntent>(Channel.UNLIMITED) // Channel to handle intents
    private val _viewState = MutableStateFlow<DogViewState>(DogViewState.BreedList(emptyMap())) // State flow for ViewState
    val viewState: StateFlow<DogViewState> = _viewState.asStateFlow() // Expose ViewState as StateFlow

    private val _randomImage = MutableStateFlow(Constants.dogImageModelSample) // State flow for the random dog image
    val randomImage: StateFlow<DogImageModel> = _randomImage.asStateFlow() // Expose randomImage as StateFlow

    init {
        processIntents() // Start processing intents
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(DogIntent.FetchAllBreeds) // Send initial intent to fetch all breeds
        }
    }

    // Function to process incoming intents
    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is DogIntent.FetchAllBreeds -> fetchAllBreeds() // Handle fetch all breeds intent
                    is DogIntent.FetchRandomImageByBreed -> fetchRandomImageByBreed(intent.breed) // Handle fetch random image by breed intent
                }
            }
        }
    }

    // Function to fetch all dog breeds from the repository
    private fun fetchAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(DogViewState.Loading) // Emit loading state
            try {
                val response = repository.getAllBreeds() // Fetch breeds from repository
                if (response.isSuccessful) {
                    _viewState.emit(DogViewState.BreedList(response.body()?.breeds ?: emptyMap())) // Emit breed list state
                } else {
                    _viewState.emit(DogViewState.Error("Failed to fetch breeds")) // Emit error state
                }
            } catch (e: Exception) {
                _viewState.emit(DogViewState.Error(e.message ?: "Unknown Error")) // Emit error state with exception message
            }
        }
    }

    // Function to fetch a random image by breed from the repository
    private fun fetchRandomImageByBreed(breed: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomImageByBreed(breed) // Fetch random image by breed
                if (response.isSuccessful) {
                    _randomImage.emit(response.body() ?: Constants.dogImageModelSample) // Emit the fetched image
                } else {
                    _viewState.emit(DogViewState.Error("Failed to fetch image")) // Emit error state
                }
            } catch (e: Exception) {
                _viewState.emit(DogViewState.Error(e.localizedMessage ?: "Unknown Error")) // Emit error state with exception message
            }
        }
    }
}