package com.incpeticode.dogbreedwitharchitecture.ui.presenter

interface DogView {
    fun showBreeds(breeds: Map<String, List<String>>)
    fun showRandomImage(imageUrl: String)
    fun setLoading(isLoading: Boolean)
}