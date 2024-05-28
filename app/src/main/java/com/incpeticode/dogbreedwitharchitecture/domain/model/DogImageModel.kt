package com.incpeticode.dogbreedwitharchitecture.domain.model

import com.google.gson.annotations.SerializedName

// Data class to model the response for a random dog image
data class DogImageModel(
    @SerializedName("message") val imageUrl: String, // URL of the dog image
    @SerializedName("status") val status: String // Status of the API response
)