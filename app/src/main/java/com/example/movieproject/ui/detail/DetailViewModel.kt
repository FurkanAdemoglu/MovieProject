package com.example.movieproject.ui.detail

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.MovieApplication
import com.example.movieproject.model.Search
import com.example.movieproject.model.movie
import com.example.movieproject.repository.MovieRepository
import com.example.movieproject.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class DetailViewModel(
    app:Application,
    val movieRepository: MovieRepository
):AndroidViewModel(app) {
    val getMovieById:MutableLiveData<Resource<movie>> = MutableLiveData()
    var getMovieByIdResponse: movie?=null
    fun getMovieById(movieId:String)=viewModelScope.launch {
        safeSearchNewsCall(movieId) }

    private suspend fun safeSearchNewsCall(movieId: String){
        getMovieById.postValue(Resource.Loading())
                val response=movieRepository.getMovieId(movieId)
        getMovieById.postValue(handleSearchNewsResponse(response)) }

    private fun handleSearchNewsResponse(response: Response<movie>): Resource<movie> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                getMovieByIdResponse=resultResponse
                return Resource.Success(resultResponse) } }
        return Resource.Error(response.message())
    } }