package com.example.nuntiumnews.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nuntiumnews.R
import com.example.nuntiumnews.models.OnBoardingItems
import com.makeramen.roundedimageview.RoundedImageView

class OnBoardingAdapter(val onBoardingItems: List<OnBoardingItems>) :
    RecyclerView.Adapter<OnBoardingAdapter.BoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardingViewHolder {
        return BoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.on_boarding_items, parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: BoardingViewHolder, position: Int) {
        holder.setImage(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class BoardingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView: RoundedImageView =
            itemView.findViewById(R.id.imageSlide)

        fun setImage(onBoardingItems : OnBoardingItems) {
            onBoardingItems.getImage()?.let { imageView.setImageResource(it) }
        }

    }
}