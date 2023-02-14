package com.example.nuntiumnews.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nuntiumnews.ui.after.BookmarkFragment
import com.example.nuntiumnews.ui.after.CategoriesFragment
import com.example.nuntiumnews.ui.after.HomeFragment
import com.example.nuntiumnews.ui.after.ProfileFragment

class BottomNavAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                CategoriesFragment()
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