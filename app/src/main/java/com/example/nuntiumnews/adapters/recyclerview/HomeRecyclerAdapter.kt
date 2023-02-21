package com.example.nuntiumnews.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nuntiumnews.R
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.ItemViewPagerBinding
import com.example.nuntiumnews.utils.setImage
import com.example.nuntiumnews.utils.textToDate
import com.example.nuntiumnews.viewmodels.SaveViewModel

class HomeRecyclerAdapter(
    var saveViewModel: SaveViewModel,
    var list: ArrayList<Article>,
    var context: Context,
    val listener: OnClickListener
) :
    RecyclerView.Adapter<HomeRecyclerAdapter.Vh>() {

    inner class Vh(var itemViewPagerBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemViewPagerBinding.root) {

        fun onBind(article: Article) {
            itemViewPagerBinding.apply {
                newsTitle.text = article.title
                dateText.text = article.publishedAt.let { textToDate(it) }
                article.urlToImage?.let { image.setImage(it) }

                var isLiked = saveViewModel.getIsLiked(article.publishedAt)

                if(isLiked == 1) {
                    saveImage.setImageResource(R.drawable.ic_saved_white)
                }

                saveImage.setOnClickListener {
                    if(isLiked == 0) {
                        isLiked = 1
                        saveImage.setImageResource(R.drawable.ic_saved_white)
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        saveViewModel.addArticle(article)
                    } else {
                        isLiked = 0
                        saveImage.setImageResource(R.drawable.ic_save_white)
                        Toast.makeText(context, "UnSaved", Toast.LENGTH_SHORT).show()
                        saveViewModel.deleteArticle(article.publishedAt)
                    }
                }
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