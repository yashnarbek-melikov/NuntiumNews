package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentProfileBinding
import com.example.nuntiumnews.utils.MySharedPreference
import com.example.nuntiumnews.utils.ThemeHelper

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mySharedPreference = MySharedPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchLayout.isChecked = mySharedPreference.getPreferences("isDark") == "1"

        binding.switchLayout.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                ThemeHelper.applyTheme(ThemeHelper.darkMode)
                activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                mySharedPreference.setPreferences("isDark", "1")
            } else {
                ThemeHelper.applyTheme(ThemeHelper.lightMode)
                mySharedPreference.setPreferences("isDark", "0")
            }
        }

        binding.card1.setOnClickListener {
            binding.switchLayout.isChecked = binding.switchLayout.isChecked != true
        }

        binding.card2.setOnClickListener {
            findNavController().navigate(R.id.action_homeNavigationFragment_to_languageFragment)
        }
    }
}