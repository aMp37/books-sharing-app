package com.example.bookshare.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.bookshare.model.User

fun ImageView.loadImageFromUrl(url: String){
    Glide.with(context)
        .load(url)
        .into(this)
}

object ImageUrlBindingAdapter{
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun fromUrl(view: ImageView, url: String?){

        if(!url.isNullOrEmpty())
            view.loadImageFromUrl(url)
    }
}