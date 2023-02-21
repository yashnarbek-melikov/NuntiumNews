package com.example.nuntiumnews.utils

import android.view.View
import android.widget.ImageView
import com.example.nuntiumnews.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url: String) {

    if (url.isEmpty()) {
        this.setImageResource(R.drawable.news_placeholder)
    } else {
        Picasso.get().load(url).placeholder(R.drawable.news_placeholder).fit().centerCrop().into(this)
    }
//    Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_background).into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}