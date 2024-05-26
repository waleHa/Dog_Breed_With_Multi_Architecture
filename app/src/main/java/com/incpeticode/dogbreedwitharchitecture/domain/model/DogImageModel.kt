package com.incpeticode.dogbreedwitharchitecture.domain.model

import com.google.gson.annotations.SerializedName

data class DogImageModel(
    @SerializedName("message") val imageUrl: String,
    @SerializedName("status") val status: String
)