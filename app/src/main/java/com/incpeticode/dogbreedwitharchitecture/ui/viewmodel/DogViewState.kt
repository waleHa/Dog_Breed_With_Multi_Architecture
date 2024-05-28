package com.incpeticode.dogbreedwitharchitecture.ui.viewmodel

sealed class DogViewState {
    data object Idle : DogViewState()
    data object Loading : DogViewState()
    data class BreedList(val breeds: Map<String, List<String>>) : DogViewState()
    data class RandomImage(val imageUrl: String) : DogViewState()
    data class Error(val message: String) : DogViewState()
}