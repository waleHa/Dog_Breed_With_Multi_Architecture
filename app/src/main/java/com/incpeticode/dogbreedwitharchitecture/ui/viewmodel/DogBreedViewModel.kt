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

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {

    val intentChannel = Channel<DogIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<DogViewState>(DogViewState.BreedList(emptyMap()))
    val viewState: StateFlow<DogViewState> = _viewState.asStateFlow()

    private val _randomImage = MutableStateFlow(Constants.dogImageModelSample)
    val randomImage: StateFlow<DogImageModel> = _randomImage.asStateFlow()

    init {
        processIntents()
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(DogIntent.FetchAllBreeds)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is DogIntent.FetchAllBreeds -> fetchAllBreeds()
                    is DogIntent.FetchRandomImageByBreed -> fetchRandomImageByBreed(intent.breed)
                }
            }
        }
    }

    private fun fetchAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(DogViewState.Loading)
            try {
                val response = repository.getAllBreeds()
                if (response.isSuccessful) {
                    _viewState.emit(DogViewState.BreedList(response.body()?.breeds ?: emptyMap()))
                } else {
                    _viewState.emit(DogViewState.Error("Failed to fetch breeds"))
                }
            } catch (e: Exception) {
                _viewState.emit(DogViewState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    private fun fetchRandomImageByBreed(breed: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomImageByBreed(breed)
                if (response.isSuccessful) {
                    _randomImage.emit(response.body() ?: Constants.dogImageModelSample)
                } else {
                    _viewState.emit(DogViewState.Error("Failed to fetch image"))
                }
            } catch (e: Exception) {
                _viewState.emit(DogViewState.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }
    }
}