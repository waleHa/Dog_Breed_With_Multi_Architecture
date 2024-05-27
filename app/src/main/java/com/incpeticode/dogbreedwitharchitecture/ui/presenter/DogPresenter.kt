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

class DogPresenter @Inject constructor(private val repository: DogRepository) {
    private var view: DogView? = null

    fun attachView(view: DogView){
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun fetchAllBreeds(){
        CoroutineScope(Dispatchers.IO).launch{
            view?.setLoading(true)
            val response = repository.getAllBreeds()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view?.showBreeds(response.body()?.breeds ?: emptyMap())
                    view?.setLoading(false)
                }
            }
            view?.setLoading(false)
        }
    }

    fun fetchRandomImageByBreed(breed: String) {
        CoroutineScope(Dispatchers.IO).launch {
            view?.setLoading(true)
            val response = repository.getRandomImageByBreed(breed)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view?.showRandomImage(response.body()?.imageUrl ?: "")
                    view?.setLoading(false)
                }
            }
        }
    }
}