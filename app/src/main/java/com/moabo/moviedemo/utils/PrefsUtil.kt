package com.moabo.moviedemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.moabo.moviedemo.BuildConfig

class PrefsUtil {

    fun getAppPrefs(context: Context): SharedPreferences? {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    fun isFirstConnection(context: Context?): Boolean? {
        return context?.let { getAppPrefs(it)!!.getBoolean("FirstCon", false) }
    }

    fun saveFirstConnection(context: Context?, save: Boolean) {
        val editor: SharedPreferences.Editor? = context?.let { getAppPrefs(it)!!.edit() }
        if (editor != null) {
            editor.putBoolean("FirstCon", save)
            editor.apply()
        }
    }
}