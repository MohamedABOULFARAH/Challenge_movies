package com.moabo.moviedemo.model.rating

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RatingRQ( @Expose
                @SerializedName("value") var value: Double)