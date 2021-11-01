package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.databinding.ItemMovieBinding
import com.example.movieproject.listener.IMovieClickListener
import com.example.movieproject.model.movie
import kotlinx.android.synthetic.main.item_movie.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallback=object : DiffUtil.ItemCallback<movie>(){
        override fun areItemsTheSame(oldItem: movie, newItem: movie): Boolean {
            return oldItem.imdbID==newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: movie, newItem: movie): Boolean {
            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(movie.poster)
                .into(ivArticleImage)
            tvSource.text=movie.director
            tvTitle.text=movie.title
            setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener:((movie)->Unit)?=null

    fun setOnItemClickListener(listener:(movie)->Unit){
        onItemClickListener=listener
    }
}