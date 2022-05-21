package com.moabo.moviedemo.model.session

import com.google.gson.annotations.SerializedName

data class SessionRS(@SerializedName("success"          ) var success        : Boolean? = null,
                     @SerializedName("guest_session_id" ) var guestSessionId : String?  = null,
                     @SerializedName("expires_at"       ) var expiresAt      : String?  = null)
{
    companion object {
        lateinit var UserSession: SessionRS
    }

    init {
        UserSession = this
    }
}
