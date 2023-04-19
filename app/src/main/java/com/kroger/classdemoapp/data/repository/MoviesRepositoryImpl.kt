package com.kroger.classdemoapp.data.repository

import com.kroger.classdemoapp.data.MoviesAPI
import com.kroger.classdemoapp.data.apiKey
import com.kroger.classdemoapp.data.model.MoviesResponseReceived
import javax.inject.Inject

//MoviesRepositoryImpl  implements  MoviesRepository interface.
class MoviesRepositoryImpl @Inject constructor(
    //moviesAPI -> calls providesMovieApi in App module and returns a retrofit object
    private val moviesAPI: MoviesAPI,
) : MoviesRepository {

    override suspend fun getMovies(): MoviesResponseReceived {
        //calls the getMovies() function on the moviesAPI object, which  return a Response object.
        //movieAPI is a retrofit object
        //moviesAPI.getMovies() -> calls end point
        //so we are calling end point on retrofit object to get response
        val result = moviesAPI.getMovies(apiKey,"us",1)

        return if (result.isSuccessful) {
            //In MoviesResponseReceived -> Success takes List<> as argument
            MoviesResponseReceived.Success(result.body()?.articles ?: emptyList())
        } else {
            //else error
            MoviesResponseReceived.Error
        }

    }
    override suspend fun getNewsPopular(): MoviesResponseReceived {
        val result = moviesAPI.getNewsPopular(apiKey,"apple","2023-03-18","2023-04-18","popularity")

        return if (result.isSuccessful) {

            MoviesResponseReceived.Success(result.body()?.articles ?: emptyList())
        } else {
            //else error
            MoviesResponseReceived.Error
        }

    }


}
