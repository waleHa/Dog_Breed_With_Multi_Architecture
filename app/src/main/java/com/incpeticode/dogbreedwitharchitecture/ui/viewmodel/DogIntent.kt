package com.incpeticode.dogbreedwitharchitecture.ui.viewmodel

// Sealed class representing the intents in MVI architecture
sealed class DogIntent {
    data object FetchAllBreeds : DogIntent() // Intent to fetch all breeds
    data class FetchRandomImageByBreed(val breed: String) : DogIntent() // Intent to fetch random image by breed
}