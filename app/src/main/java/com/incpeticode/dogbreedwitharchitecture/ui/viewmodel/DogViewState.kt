package com.incpeticode.dogbreedwitharchitecture.ui.viewmodel

// Sealed class representing the view states in MVI architecture
sealed class DogViewState {
    data object Idle : DogViewState() // Idle state
    data object Loading : DogViewState() // Loading state
    data class BreedList(val breeds: Map<String, List<String>>) : DogViewState() // State with the list of breeds
    data class RandomImage(val imageUrl: String) : DogViewState() // State with a random image URL
    data class Error(val message: String) : DogViewState() // Error state with an error message
}