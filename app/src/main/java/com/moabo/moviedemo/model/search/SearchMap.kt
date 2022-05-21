package com.moabo.moviedemo.model.search

import com.moabo.moviedemo.model.movie.Movie

data class SearchMap(val page: Int, val results : ArrayList<Movie>, val totalResults : Int, val totalPages : Int)
