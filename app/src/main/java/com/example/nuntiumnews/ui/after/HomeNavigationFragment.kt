package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.viewpager.BottomNavAdapter
import com.example.nuntiumnews.databinding.FragmentHomeNavigationBinding

class HomeNavigationFragment : Fragment(R.layout.fragment_home_navigation) {

    private var _binding: FragmentHomeNavigationBinding? = null
    private var bottomNavAdapter: BottomNavAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeNavigationBinding.bind(view)

        bottomNavAdapter = BottomNavAdapter(this)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        _binding?.apply {
            viewPager2.adapter = bottomNavAdapter
            viewPager2.isUserInputEnabled = false
            bottomNavigationView.menu.findItem(R.id.home_vector).isChecked = true

            bottomNavigationView.setOnItemSelectedListener {

    //                for (i in 0 until bottomNavigationView.menu.size()) {
    //                    val item = bottomNavigationView.menu.getItem(i)
    //                    val isChecked = item.itemId == it.itemId
    //                    item.isChecked = isChecked
    //                }

                when (it.itemId) {
                    R.id.home_vector -> {
                        viewPager2.currentItem = 0
                    }
                    R.id.category_vector -> {
                        viewPager2.currentItem = 1
                    }
                    R.id.saved_vector -> {
                        viewPager2.currentItem = 2
                    }
                    R.id.profile_vector -> {
                        viewPager2.currentItem = 3
                    }
                }

                return@setOnItemSelectedListener true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavAdapter = null
        _binding = null
    }
}