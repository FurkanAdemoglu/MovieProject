package com.example.movieproject.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieproject.repository.MovieRepository
import com.example.movieproject.ui.detail.DetailViewModel
import com.example.movieproject.ui.list.ListViewModel


class DetailViewModelProviderFactory(
    val app: Application,
    val movieRepository: MovieRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(app,movieRepository) as T
    }


}