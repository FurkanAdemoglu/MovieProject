package com.example.movieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movieproject.repository.MovieRepository
import com.example.movieproject.ui.ListViewModelProviderFactory
import com.example.movieproject.ui.list.ListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository=MovieRepository()

        val viewModelProviderFactory=ListViewModelProviderFactory(application,newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(ListViewModel::class.java)
    }
}