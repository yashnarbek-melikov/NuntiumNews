package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        binding.category1.setOnClickListener(this)
        binding.category2.setOnClickListener(this)
        binding.category3.setOnClickListener(this)
        binding.category4.setOnClickListener(this)
        binding.category5.setOnClickListener(this)
        binding.category6.setOnClickListener(this)
        binding.category7.setOnClickListener(this)
        binding.category8.setOnClickListener(this)
        binding.category9.setOnClickListener(this)
        binding.category10.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.category1 -> {
            }
            R.id.category2 -> {
            }
            R.id.category3 -> {
            }
            R.id.category4 -> {
            }
            R.id.category5 -> {
            }
            R.id.category6 -> {
            }
            R.id.category7 -> {
            }
            R.id.category8 -> {
            }
            R.id.category9 -> {
            }
            R.id.category10 -> {
            }
        }
    }
}