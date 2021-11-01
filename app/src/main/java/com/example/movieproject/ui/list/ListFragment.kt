package com.example.movieproject.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieproject.MainActivity
import com.example.movieproject.R
import com.example.movieproject.adapter.ListAdapter
import com.example.movieproject.util.Constants.SEARCH_NEWS_TIME_DELAY
import com.example.movieproject.util.Resource
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListFragment : Fragment(R.layout.fragment_list) {
        lateinit var viewModel:ListViewModel
        lateinit var listAdapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecyclerView()

        listAdapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_listFragment_to_detailFragment
            )
        }

        var job: Job?=null
        etSearch.addTextChangedListener { editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response->
            Log.v("Response",response.data.toString())
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { search ->
                        listAdapter.differ.submitList(search.resultSearch)
                    }
                }

                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })
    }

    private fun setupRecyclerView(){
        listAdapter= ListAdapter()
        rvSearchNews.apply {
            adapter=listAdapter
            layoutManager= LinearLayoutManager(activity)

        }
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility=View.INVISIBLE
        isLoading=false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE
        isLoading=true
    }

    var isLoading=false
    var isLastPage=false
    var isScrolling=false
    }

