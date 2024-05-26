package com.incpeticode.dogbreedwitharchitecture.data.remote

import com.incpeticode.dogbreedwitharchitecture.domain.model.BreedListModel
import com.incpeticode.dogbreedwitharchitecture.domain.model.DogImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedListModel>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(@Path("breed") breed: String): Response<DogImageModel>
}