package com.kroger.classdemoapp.di

import com.kroger.classdemoapp.data.repository.MoviesRepository
import com.kroger.classdemoapp.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRep(
        moviesRepositoryImpl: MoviesRepositoryImpl,
    ): MoviesRepository
}
