package com.kroger.classdemoapp.data

import com.kroger.classdemoapp.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey:String = "831d29b9d8644d519bf696197aa3aa57"
const val baseUrl : String = "https://newsapi.org/"
interface MoviesAPI {
    //@GET("v2/top-headlines?apiKey=$apiKey")
    @GET("v2/top-headlines")
    suspend fun getMovies(@Query("apiKey") apiKey: String,@Query("country")country:String, @Query("page") page:Int): Response<MoviesResponse>
    @GET("v2/everything")
    suspend fun getNewsPopular(@Query("apiKey") apiKey: String, @Query("q")topic:String,
                               @Query("from") dateStart:String, @Query("to") dateEnd:String, @Query("sortBy") sortingBy:String): Response<MoviesResponse>
}

//cae3431d75d9a51a8b919e6bec6a8508


