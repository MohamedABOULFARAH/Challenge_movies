package com.moabo.moviedemo.model.topRated

import com.google.gson.annotations.SerializedName
import com.moabo.moviedemo.model.movie.Movie

data class TopRatedMap (

    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null

)
