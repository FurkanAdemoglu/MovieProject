package com.example.movieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movieproject.repository.MovieRepository
import com.example.movieproject.ui.DetailViewModelProviderFactory
import com.example.movieproject.ui.ListViewModelProviderFactory
import com.example.movieproject.ui.detail.DetailViewModel
import com.example.movieproject.ui.list.ListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    lateinit var viewModelDetail:DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository=MovieRepository()

        val viewModelProviderFactory=ListViewModelProviderFactory(application,newsRepository)
        val viewModelProviderFactoryDetail=DetailViewModelProviderFactory(application,newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(ListViewModel::class.java)
        viewModelDetail= ViewModelProvider(this,viewModelProviderFactoryDetail).get(DetailViewModel::class.java)

    }
}