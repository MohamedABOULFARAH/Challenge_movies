package com.moabo.moviedemo.model.movie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie(

    @Expose
    @SerializedName("id")
    @PrimaryKey
    var id : Int?,

    @Expose
    @SerializedName("adult")
    var adult : Boolean?,

    @Expose
    @SerializedName("backdrop_path")
    var backdropPath : String?,


    @Expose
    @SerializedName("original_language")
    var originalLanguage : String?,

    @Expose
    @SerializedName("original_title")
    var originalTitle : String?,

    @Expose
    @SerializedName("overview")
    var overview : String?,

    @Expose
    @SerializedName("popularity")
    var popularity : Double?,

    @Expose
    @SerializedName("poster_path")
    var posterPath : String?,

    @Expose
    @SerializedName("release_date")
    var releaseDate : String?,

    @Expose
    @SerializedName("title")
    var title : String?,

    @Expose
    @SerializedName("video")
    var video : Boolean?,

    @Expose
    @SerializedName("vote_average")
    var voteAverage : Double?,

    @Expose
    @SerializedName("vote_count")
    var voteCount  : Int? ,

    @Expose
    @SerializedName("isFavorite")
    var isFavorite  : Boolean?=false
): Parcelable
