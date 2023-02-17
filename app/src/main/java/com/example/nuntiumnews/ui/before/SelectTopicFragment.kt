package com.example.nuntiumnews.ui.before

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentSelectTopicBinding
import com.example.nuntiumnews.utils.MySharedPreference

class SelectTopicFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSelectTopicBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectTopicBinding.inflate(inflater, container, false)

        mySharedPreference = MySharedPreference(requireContext())

        setCardBackground("sports", "sport", binding.category1, binding.sportTv)
        setCardBackground("life", "life", binding.category2, binding.lifeTv)
        setCardBackground("animals", "animal", binding.category3, binding.animalsTv)
        setCardBackground("food", "food", binding.category4, binding.foodTv)
        setCardBackground("history", "history", binding.category5, binding.historyTv)
        setCardBackground("politics", "politic", binding.category6, binding.politicTv)
        setCardBackground("gaming", "gaming", binding.category7, binding.gamingTv)
        setCardBackground("nature", "nature", binding.category8, binding.natureTv)
        setCardBackground("art", "art", binding.category9, binding.artTv)
        setCardBackground("fashion", "fashion", binding.category10, binding.fashionTv)

        binding.buttonNext.setOnClickListener {
            val all = requireContext().getSharedPreferences("have", Context.MODE_PRIVATE).all
            if (all.values.isNotEmpty()) {
                findNavController().navigate(R.id.action_selectTopicFragment_to_homeNavigationFragment)
            } else {
                Toast.makeText(requireContext(), "Choose topic", Toast.LENGTH_SHORT).show()
            }
        }
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
                setCardBackgroundWhenClicked("sports", "sport", binding.category1, binding.sportTv)
            }
            R.id.category2 -> {
                setCardBackgroundWhenClicked("life", "life", binding.category2, binding.lifeTv)
            }
            R.id.category3 -> {
                setCardBackgroundWhenClicked(
                    "animals",
                    "animal",
                    binding.category3,
                    binding.animalsTv
                )
            }
            R.id.category4 -> {
                setCardBackgroundWhenClicked("food", "food", binding.category4, binding.foodTv)
            }
            R.id.category5 -> {
                setCardBackgroundWhenClicked(
                    "history",
                    "history",
                    binding.category5,
                    binding.historyTv
                )
            }
            R.id.category6 -> {
                setCardBackgroundWhenClicked(
                    "politics",
                    "politic",
                    binding.category6,
                    binding.politicTv
                )
            }
            R.id.category7 -> {
                setCardBackgroundWhenClicked(
                    "gaming",
                    "gaming",
                    binding.category7,
                    binding.gamingTv
                )
            }
            R.id.category8 -> {
                setCardBackgroundWhenClicked(
                    "nature",
                    "nature",
                    binding.category8,
                    binding.natureTv
                )
            }
            R.id.category9 -> {
                setCardBackgroundWhenClicked("art", "art", binding.category9, binding.artTv)
            }
            R.id.category10 -> {
                setCardBackgroundWhenClicked(
                    "fashion",
                    "fashion",
                    binding.category10,
                    binding.fashionTv
                )
            }
        }
    }

    private fun setCardBackground(
        key: String,
        value: String,
        cardView: CardView,
        textView: TextView
    ) {
        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
        val cardColor = ContextCompat.getColor(requireContext(), R.color.search_card_color)
        val textColor = ContextCompat.getColor(requireContext(), R.color.card_text_color)
        val textWhite = ContextCompat.getColor(requireContext(), R.color.white)

        if (mySharedPreference?.getPreferences(key) != null) {
            if (mySharedPreference?.getPreferences(key) == value) {
                cardView.setCardBackgroundColor(nuntiumColor)
                textView.setTextColor(textWhite)
            } else {
                cardView.setCardBackgroundColor(cardColor)
                textView.setTextColor(textColor)
            }
        } else {
            cardView.setCardBackgroundColor(cardColor)
            textView.setTextColor(textColor)
        }
    }

    private fun setCardBackgroundWhenClicked(
        key: String,
        value: String,
        cardView: CardView,
        textView: TextView
    ) {

        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
        val cardColor = ContextCompat.getColor(requireContext(), R.color.search_card_color)
        val textColor = ContextCompat.getColor(requireContext(), R.color.card_text_color)
        val textWhite = ContextCompat.getColor(requireContext(), R.color.white)

        if (mySharedPreference?.getPreferences(key) != null) {
            if (mySharedPreference?.getPreferences(key) == value) {
                mySharedPreference?.setPreferences(key, null)
                cardView.setCardBackgroundColor(cardColor)
                textView.setTextColor(textColor)
            } else {
                mySharedPreference?.setPreferences(key, value)
                cardView.setCardBackgroundColor(nuntiumColor)
                textView.setTextColor(textWhite)
            }
        } else {
            mySharedPreference?.setPreferences(key, value)
            cardView.setCardBackgroundColor(nuntiumColor)
            textView.setTextColor(textWhite)
        }
    }

}