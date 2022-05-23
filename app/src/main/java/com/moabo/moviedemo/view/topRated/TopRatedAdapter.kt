package com.moabo.moviedemo.view.topRated

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moabo.moviedemo.BR
import com.moabo.moviedemo.databinding.TopRatedMovieRowBinding
import com.moabo.moviedemo.model.movie.Movie

import javax.inject.Inject

class TopRatedAdapter @Inject constructor(private var onMovieClicked: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {

    private val moviesList: MutableList<Movie> = mutableListOf()

    inner class TopRatedViewHolder(private val binding: TopRatedMovieRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.setVariable(BR.movie, movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        val binding = TopRatedMovieRowBinding.inflate(LayoutInflater.from(parent.context))
        return TopRatedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.itemView.setOnClickListener {
            movie?.let { it1 -> onMovieClicked.invoke(it1) }
        }
        holder.bind(movie)
    }

    override fun getItemCount() = moviesList.size

    fun setData(movies: List<Movie>) {
        this.moviesList.clear()
        this.moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun orderListByTitle(isAsc: Boolean) {
        val sortedAppsList: List<Movie>
        if (isAsc) {
            sortedAppsList = moviesList.sortedBy { it.title }
            setData(sortedAppsList)
        } else {
            sortedAppsList = moviesList.sortedByDescending { it.title }
            setData(sortedAppsList)
        }

    }

    fun orderListByDate(isAsc: Boolean) {
        val sortedAppsList: List<Movie>
        if (isAsc) {
            sortedAppsList = moviesList.sortedBy { it.releaseDate }
            setData(sortedAppsList)
        } else {
            sortedAppsList = moviesList.sortedByDescending { it.releaseDate }
            setData(sortedAppsList)
        }
    }
}
