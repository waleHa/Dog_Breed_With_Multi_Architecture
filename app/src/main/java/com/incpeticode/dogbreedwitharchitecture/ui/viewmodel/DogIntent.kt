package com.incpeticode.dogbreedwitharchitecture.ui.viewmodel

sealed class DogIntent {
    data object FetchAllBreeds : DogIntent()
    data class FetchRandomImageByBreed(val breed: String) : DogIntent()
}