package com.example.nuntiumnews.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nuntiumnews.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String) {

    Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background).into(this)
//    Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_background).into(this)
}