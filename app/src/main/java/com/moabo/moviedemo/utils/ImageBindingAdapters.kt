
package com.moabo.moviedemo.utils

import android.R
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("imageUrl", "placeholder")
fun ImageView.loadImageForAdapter(url: String, placeholder: Drawable) {
  val media="https://image.tmdb.org/t/p/original/$url"
  Glide.with(this)
    .load(media)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .placeholder(R.drawable.ic_menu_gallery)
    .into(this)}

fun loadImage(url:String,resourceId:ImageView){

  val media="https://image.tmdb.org/t/p/original/$url"
  Glide.with(resourceId.context)
    .load(media)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .placeholder(R.drawable.ic_menu_gallery)
    .into(resourceId)

}


