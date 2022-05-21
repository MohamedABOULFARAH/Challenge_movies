package com.moabo.moviedemo.view.topRated

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Util.loadImage
import com.moabo.moviedemo.databinding.TopRatedMovieRowBinding
import com.moabo.moviedemo.model.movie.Movie

class TopRatedMoviesAdapter(
    private var moviesList: List<Movie>,
    private var onMovieClicked: (movieId: Int) -> Unit
) : RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesViewHolder>() {
    private lateinit var binding: TopRatedMovieRowBinding
    private  var alphaSorted: Boolean=false
    private  var dateSorted: Boolean=false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMoviesViewHolder {
        binding = TopRatedMovieRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TopRatedMoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TopRatedMoviesViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = moviesList[position]
        binding.topRatedTitleMovie.text = item.title
        binding.topRatedReleaseDateMovie.text = item.releaseDate
        binding.topRatedRatingMovie.text = item.voteAverage.toString()
        binding.topRatedViewsMovie.text = item.voteCount.toString()
        item.posterPath?.let { loadImage(it, binding.topRatedImageMovie) }
        holder.itemView.setOnClickListener {
            item.id?.let { it1 -> onMovieClicked.invoke(it1) }
        }

    }

    override fun getItemCount(): Int = moviesList.size

    class TopRatedMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    fun orderListByTitle() {
        if (!alphaSorted){
            val sortedAppsList = moviesList.sortedBy { it.title }
            setData(sortedAppsList)
        }

    }

    fun orderListByDate() {
        val sortedAppsList = moviesList.sortedByDescending { it.releaseDate }
        setData(sortedAppsList)
    }

    fun setData(movies: List<Movie>) {
        this.moviesList = movies
        notifyDataSetChanged()
    }

}