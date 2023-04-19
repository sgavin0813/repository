package com.kroger.classdemoapp.data.repository

import com.kroger.classdemoapp.data.model.MoviesResponseReceived
//interface implementation in MoviesRepositoryImpl
interface MoviesRepository {
    suspend fun getMovies(): MoviesResponseReceived
    suspend fun getNewsPopular(): MoviesResponseReceived
}
