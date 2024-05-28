package com.incpeticode.dogbreedwitharchitecture.domain.model

import com.google.gson.annotations.SerializedName


// Data class to model the response for the list of dog breeds
data class BreedListModel(
    @SerializedName("message") val breeds: Map<String, List<String>>, // Map of breed names to sub-breed lists
    @SerializedName("status") val status: String // Status of the API response
)