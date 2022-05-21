package com.moabo.moviedemo.model.rating

import com.google.gson.annotations.SerializedName

data class RatingRS(
    @SerializedName("success"        ) var success       : Boolean? = null,
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("status_message" ) var statusMessage : String?  = null

)
