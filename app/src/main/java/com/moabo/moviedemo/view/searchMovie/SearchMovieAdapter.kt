package com.moabo.moviedemo.view.searchMovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moabo.moviedemo.utils.loadImage
import com.moabo.moviedemo.databinding.SearchMovieRowBinding
import com.moabo.moviedemo.model.movie.Movie

class SearchMovieAdapter(private var searchResulsList: List<Movie>, private var onMovieClicked:(movie:Movie)->Unit) :
    RecyclerView.Adapter<SearchMovieAdapter.SearchMovieHolder>() {
    private lateinit var binding: SearchMovieRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieHolder {
        binding = SearchMovieRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return SearchMovieHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchMovieHolder, position: Int) {
        val item = searchResulsList[position]
        binding.searchTitleMovie.text = item.title
        binding.searchReleaseDateMovie.text = item.releaseDate
        item.posterPath?.let { loadImage(it,binding.searchImageMovie) }
        holder.itemView.setOnClickListener {
            item.let { it1 -> onMovieClicked.invoke(it1) }
        }
    }

    override fun getItemCount(): Int = searchResulsList.size

    class SearchMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setData(searchList: List<Movie>) {
        this.searchResulsList = searchList
        notifyDataSetChanged()
    }

}