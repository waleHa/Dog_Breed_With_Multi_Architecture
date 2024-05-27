package com.incpeticode.dogbreedwitharchitecture.ui

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {

    private val _breedList = MutableStateFlow<BreedListModel?>(null)
    val breedList: StateFlow<BreedListModel?> = _breedList.asStateFlow()

    private val _randomImage = MutableStateFlow(Constants.dogImageModelSample)
    val randomImage: StateFlow<DogImageModel> = _randomImage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchAllBreeds()
    }

    fun fetchAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val response = repository.getAllBreeds()
            if (response.isSuccessful) {
                _breedList.value = response.body()
            }
            _isLoading.value = false
        }
    }

    fun fetchRandomImageByBreed(breed: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val response = repository.getRandomImageByBreed(breed)
            if (response.isSuccessful) {
                _randomImage.value = response.body()!!
            }
            _isLoading.value = false
        }
    }
}