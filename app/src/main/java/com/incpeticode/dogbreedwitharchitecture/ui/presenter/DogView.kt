package com.incpeticode.dogbreedwitharchitecture.ui.presenter

// Interface representing the View in MVP pattern
interface DogView {
    fun showBreeds(breeds: Map<String, List<String>>) // Show list of breeds
    fun showRandomImage(imageUrl: String) // Show a random image by breed
    fun setLoading(isLoading: Boolean) // Show or hide loading indicator
}