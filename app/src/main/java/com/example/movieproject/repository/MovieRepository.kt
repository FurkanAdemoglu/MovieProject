package com.example.movieproject.repository

import com.example.movieproject.network.RetrofitInstance

class MovieRepository {

    suspend fun searchNews(searchQuery:String)=
        RetrofitInstance.api.search(searchQuery)
}