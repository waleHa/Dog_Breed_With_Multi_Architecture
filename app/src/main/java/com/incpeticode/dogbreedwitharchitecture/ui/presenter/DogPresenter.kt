package com.incpeticode.dogbreedwitharchitecture.ui.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incpeticode.dogbreedwitharchitecture.data.repository.DogRepository
import com.incpeticode.dogbreedwitharchitecture.data.util.Constants
import com.incpeticode.dogbreedwitharchitecture.domain.model.BreedListModel
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Presenter class to handle the logic for the DogView
class DogPresenter @Inject constructor(private val repository: DogRepository) {
    private var view: DogView? = null

    // Attaches the view to the presenter
    fun attachView(view: DogView){
        this.view = view
    }

    // Detaches the view from the presenter to avoid memory leaks
    fun detachView() {
        view = null
    }

    // Fetches all dog breeds from the repository
    fun fetchAllBreeds(){
        CoroutineScope(Dispatchers.IO).launch{
            view?.setLoading(true) // Show loading indicator
            val response = repository.getAllBreeds() // Fetch breeds from the repository
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) { // Switch to the main thread to update the UI
                    view?.showBreeds(response.body()?.breeds ?: emptyMap()) // Display the breeds
                    view?.setLoading(false) // Hide loading indicator
                }
            } else {
                view?.setLoading(false) // Hide loading indicator if the request fails
            }
        }
    }

    // Fetches a random image by breed from the repository
    fun fetchRandomImageByBreed(breed: String) {
        CoroutineScope(Dispatchers.IO).launch {
            view?.setLoading(true) // Show loading indicator
            val response = repository.getRandomImageByBreed(breed) // Fetch image from the repository
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) { // Switch to the main thread to update the UI
                    view?.showRandomImage(response.body()?.imageUrl ?: "") // Display the image
                    view?.setLoading(false) // Hide loading indicator
                }
            }
        }
    }
}