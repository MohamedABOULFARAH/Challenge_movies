package com.moabo.moviedemo.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(

    @Expose
    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @Expose
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
    @Expose
    @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int> = arrayListOf(),
    @Expose
    @SerializedName("id"                ) var id               : Int?           = null,
    @Expose
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @Expose
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @Expose
    @SerializedName("overview"          ) var overview         : String?        = null,
    @Expose
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @Expose
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @Expose
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @Expose
    @SerializedName("title"             ) var title            : String?        = null,
    @Expose
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @Expose
    @SerializedName("vote_average"      ) var voteAverage      : Double?        = null,
    @Expose
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null)
