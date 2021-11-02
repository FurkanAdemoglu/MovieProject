package com.example.movieproject.network

import com.example.movieproject.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {//Lazy means that we only initialize
            //this here once which is put in curly brackets
            //http interceptor gelen responsumuzu görmek için oluşturduğumuz yapı
            val logging= HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client= OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build() }
        val api by lazy {
            retrofit.create(MovieShowService::class.java) } } }