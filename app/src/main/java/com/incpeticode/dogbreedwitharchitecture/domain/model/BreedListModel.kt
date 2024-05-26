package com.incpeticode.dogbreedwitharchitecture.domain.model

import com.google.gson.annotations.SerializedName


data class BreedListModel(
    @SerializedName("message") val breeds: Map<String, List<String>>,
    @SerializedName("status") val status: String
)