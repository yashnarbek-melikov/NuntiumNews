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

class HomeRecyclerAdapter(val listener: OnClickListener): ListAdapter<Article, HomeRecyclerAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(var itemViewPagerBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemViewPagerBinding.root) {

        fun onBind(article: Article) {
            itemViewPagerBinding.apply {
                newsTitle.text = article.title
                article.urlToImage?.let { image.setImage(it) }
            }
            itemView.setOnClickListener {
                listener.onImageClick(article)
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    interface OnClickListener {
        fun onImageClick(article: Article)
    }
}