package com.example.movieproject.ui.list

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
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

class ListViewModel(
    app:Application,
    val movieRepository: MovieRepository
):AndroidViewModel(app) {
    val searchMovies:MutableLiveData<Resource<Search>> = MutableLiveData()
    var searchMovieResponse:Search?=null

    fun searchNews(searchQuery:String)=viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }

    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchMovies.postValue(Resource.Loading())
        try{
            if(hasInternetConnection()){
                val response=movieRepository.searchNews(searchQuery)
                searchMovies.postValue(handleSearchNewsResponse(response))
            }else{
                searchMovies.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException ->searchMovies.postValue(Resource.Error("Network Failure"))
                else->searchMovies.postValue(Resource.Error("Conversion Error"))
            } } }


    private fun handleSearchNewsResponse(response: Response<Search>):Resource<Search>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                if(searchMovieResponse==null){
                    searchMovieResponse=resultResponse
                }else{
                    val oldArticles=searchMovieResponse?.resultSearch
                    val newArticles=resultResponse.resultSearch
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(resultResponse) } }

        return Resource.Error(response.message()) }
    private fun hasInternetConnection():Boolean{
        val connectivityManager=getApplication<MovieApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val activeNetwork=connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI)->true
                capabilities.hasTransport(TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(TRANSPORT_ETHERNET)->true
                else->false }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI->true
                    TYPE_MOBILE->true
                    TYPE_ETHERNET->true
                    else->false
                } } }
        return false } }