package com.example.movieproject.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieproject.MainActivity
import com.example.movieproject.R
import com.example.movieproject.util.Resource
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_movie.view.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args:DetailFragmentArgs by navArgs()
    lateinit var viewModel: DetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModelDetail
        viewModel.getMovieById(args.movieId)

        viewModel.getMovieById.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { search ->
                        Glide.with(this)
                            .load(search.poster)
                            .into(imageViewMovie)
                        textViewMovieName.text=search.title
                        textViewMovieOverview.text=search.plot
                        textViewMovieReleaseDate.text="Movie Released Date\n"+search.released
                        imdbRating.text="Movie Imdb Rating:"+search.imdbRating
                    } }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_SHORT).show()
                    } }
                is Resource.Loading->{
                    showProgressBar()
                } } }) }

    private fun hideProgressBar(){
        paginationProgressBarDetail.visibility=View.INVISIBLE
        isLoading=false }

    private fun showProgressBar(){
        paginationProgressBarDetail.visibility=View.VISIBLE
        isLoading=true }
    var isLoading=false
}