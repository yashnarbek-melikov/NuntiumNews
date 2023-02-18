package com.example.nuntiumnews.utils

import android.widget.ImageView
import com.example.nuntiumnews.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String) {

    Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.news_placeholder).into(this)
//    Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_background).into(this)
}