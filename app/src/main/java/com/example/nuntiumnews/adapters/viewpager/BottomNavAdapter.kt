package com.example.nuntiumnews.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nuntiumnews.ui.after.*

class BottomNavAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                RecommendedFragment()
            }
            2 -> {
                BookmarkFragment()
            }
            3 -> {
                ProfileFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }
}