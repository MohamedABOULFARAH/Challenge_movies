package com.moabo.moviedemo.utils

import android.R
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast


sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:Any) : ApiState()
    object Empty : ApiState()
}



fun showAlert(context:Context,message :String){
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Alert")
    builder.setMessage(message)

    builder.setPositiveButton(R.string.ok) { _, _ ->
        Toast.makeText(context,
            R.string.ok, Toast.LENGTH_SHORT).show()
    }

    builder.setNegativeButton(R.string.cancel) { _, _ ->
        Toast.makeText(context,
            R.string.cancel, Toast.LENGTH_SHORT).show()
    }


    builder.show()
}
