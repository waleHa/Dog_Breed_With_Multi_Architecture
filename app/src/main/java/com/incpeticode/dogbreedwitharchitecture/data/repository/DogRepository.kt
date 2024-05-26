package com.incpeticode.dogbreedwitharchitecture.data.repository

import com.incpeticode.dogbreedwitharchitecture.data.remote.DogBreedApiService
import com.incpeticode.dogbreedwitharchitecture.domain.model.BreedListModel
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import retrofit2.Response
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogApiService: DogBreedApiService) {

    suspend fun getAllBreeds(): Response<BreedListModel> = dogApiService.getAllBreeds()

    suspend fun getRandomImageByBreed(breed: String): Response<DogImageModel> =
        dogApiService.getRandomImageByBreed(breed)
}