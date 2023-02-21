package com.example.nuntiumnews.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.ItemRecyclerSavedBinding
import com.example.nuntiumnews.utils.setImage
import com.example.nuntiumnews.utils.textToDate

class RecommendedRecyclerAdapter(var list: ArrayList<Article>, val listener: OnClickListener) :
    RecyclerView.Adapter<RecommendedRecyclerAdapter.Vh>(){

    inner class Vh(var itemRecyclerSavedBinding: ItemRecyclerSavedBinding) :
        RecyclerView.ViewHolder(itemRecyclerSavedBinding.root) {

        fun onBind(newsModel: Article) {
            itemRecyclerSavedBinding.apply {
                newsModel.urlToImage?.let { imageNew.setImage(it) }

                nameTv.text = textToDate(newsModel.publishedAt)
                newsTitle.text = newsModel.title
            }
            itemView.setOnClickListener {
                listener.onImageClick(newsModel)
            }
        }
    }

//    class MyDiffUtil : DiffUtil.ItemCallback<Article>() {
//        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem.urlToImage == newItem.urlToImage
//        }
//
//        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem == newItem
//        }
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRecyclerSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface OnClickListener {
        fun onImageClick(newsModel: Article)
    }
}