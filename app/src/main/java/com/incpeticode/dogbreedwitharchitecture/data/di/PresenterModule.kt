package com.incpeticode.dogbreedwitharchitecture.data.di

import com.incpeticode.dogbreedwitharchitecture.data.repository.DogRepository
import com.incpeticode.dogbreedwitharchitecture.ui.presenter.DogPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
object PresenterModule {
    @Provides
    fun provideDogPresenter(repository: DogRepository): DogPresenter {
        return DogPresenter(repository)
    }
}
