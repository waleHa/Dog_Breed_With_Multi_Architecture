package com.incpeticode.dogbreedwitharchitecture.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incpeticode.dogbreedwitharchitecture.data.repository.DogRepository
import com.incpeticode.dogbreedwitharchitecture.domain.model.BreedListModel
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {
    private val dogImageModel =
        DogImageModel("https://cdn.britannica.com/60/12460-004-54537CCC.jpg", "success")
    private val _breedList = MutableLiveData<BreedListModel>()
    val breedList: LiveData<BreedListModel> = _breedList

    private val _randomImage = MutableLiveData<DogImageModel>(dogImageModel)
    val randomImage: LiveData<DogImageModel> = _randomImage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchAllBreeds()
    }

    fun fetchAllBreeds() {
        viewModelScope.launch {
            val response = repository.getAllBreeds()
            if (response.isSuccessful) {
                _breedList.value = response.body()
            }
        }
    }

    fun fetchRandomImageByBreed(breed: String) {
        viewModelScope.launch {
            val response = repository.getRandomImageByBreed(breed)
            if (response.isSuccessful) {
                _randomImage.value = response.body()
            }
        }
    }
}

