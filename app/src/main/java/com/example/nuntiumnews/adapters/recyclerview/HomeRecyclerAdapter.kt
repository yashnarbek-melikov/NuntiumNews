package com.example.nuntiumnews.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nuntiumnews.databinding.ItemViewPagerBinding
import com.example.nuntiumnews.models.newsModel.Article
import com.example.nuntiumnews.utils.setImage
import com.example.nuntiumnews.utils.textToDate

class HomeRecyclerAdapter(var list: ArrayList<Article>, val listener: OnClickListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.Vh>() {

    inner class Vh(var itemViewPagerBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemViewPagerBinding.root) {

        fun onBind(article: Article) {
            itemViewPagerBinding.apply {
                newsTitle.text = article.title
                dateText.text = article.publishedAt?.let { textToDate(it) }
                article.urlToImage?.let { image.setImage(it) }
            }
            itemView.setOnClickListener {
                listener.onImageClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface OnClickListener {
        fun onImageClick(article: Article)
    }
}