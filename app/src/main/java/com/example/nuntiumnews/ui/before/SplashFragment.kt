package com.example.nuntiumnews.ui.before

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentSplashBinding
import com.example.nuntiumnews.ui.after.HomeNavigationFragment
import com.example.nuntiumnews.utils.MySharedPreference
import kotlinx.coroutines.delay

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        handler = Handler()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.nuntium_color)


        lifecycleScope.launchWhenStarted {
            delay(2000)
            val all = requireContext().getSharedPreferences("have", Context.MODE_PRIVATE).all
            if (all.values.isNotEmpty()) {
                findNavController().navigate(R.id.action_splashFragment_to_homeNavigationFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler = null
        _binding = null
    }
}