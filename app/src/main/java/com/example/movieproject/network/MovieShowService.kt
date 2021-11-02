package com.example.movieproject.network

import com.example.movieproject.model.Search
import com.example.movieproject.model.movie
import com.example.movieproject.util.Constants.OMDB_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieShowService {

    @GET("/")
    suspend fun search(@Query("s") searchText:String,@Query("apikey") omdb_api_key:String=OMDB_API_KEY):Response<Search>

    @GET("/")
    suspend fun getMovieById(@Query("i") movieId: String, @Query("apikey") ombd_api_key: String = OMDB_API_KEY): Response<movie>

}