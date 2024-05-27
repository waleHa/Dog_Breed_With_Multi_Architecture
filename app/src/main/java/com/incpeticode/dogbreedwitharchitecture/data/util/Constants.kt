package com.incpeticode.dogbreedwitharchitecture.data.util

import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel

object Constants {
    const val ALL_BREEDS_END_POINT = "breeds/list/all"
    const val RANDOM_BREED_END_POINT = "breed/{breed}/images/random"
    const val BASE_URL = "https://dog.ceo/api/"

    val dogImageModelSample =
        DogImageModel("https://cdn.britannica.com/60/12460-004-54537CCC.jpg", "success")

}