package com.example.demo.Util

import android.app.AlertDialog
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.util.*

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:Any) : ApiState()
    object Empty : ApiState()
}
fun loadImage(url:String,resourceId:ImageView){
    Picasso.get().load("https://image.tmdb.org/t/p/original/$url").networkPolicy(NetworkPolicy.OFFLINE).into(resourceId)

}


fun showAlert(context:Context,message :String){
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Androidly Alert")
    builder.setMessage(message)

    builder.setPositiveButton(android.R.string.yes) { _, _ ->
        Toast.makeText(context,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }

    builder.setNegativeButton(android.R.string.no) { _, _ ->
        Toast.makeText(context,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }


    builder.show()
}
