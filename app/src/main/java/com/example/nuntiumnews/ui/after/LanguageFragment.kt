package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentLanguageBinding
import com.example.nuntiumnews.utils.MySharedPreference
import java.util.*

class LanguageFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)

        binding.backCard.setOnClickListener {
            findNavController().popBackStack()
        }
        mySharedPreference = MySharedPreference(requireContext())

        setCardBackground("english", binding.card1, binding.text1, binding.img1)
        setCardBackground("turkish", binding.card2, binding.text2, binding.img2)
        setCardBackground("german", binding.card3, binding.text3, binding.img3)
        setCardBackground("spanish", binding.card4, binding.text4, binding.img4)

        binding.card1.setOnClickListener(this)
        binding.card2.setOnClickListener(this)
        binding.card3.setOnClickListener(this)
        binding.card4.setOnClickListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.card1 -> {
                languageCardClick("english", binding.card1, binding.text1, binding.img1)
                switchOffOthers(binding.card2, binding.text2, binding.img2)
                switchOffOthers(binding.card3, binding.text3, binding.img3)
                switchOffOthers(binding.card4, binding.text4, binding.img4)
            }
            R.id.card2 -> {
                languageCardClick("turkish", binding.card2, binding.text2, binding.img2)
                switchOffOthers(binding.card1, binding.text1, binding.img1)
                switchOffOthers(binding.card3, binding.text3, binding.img3)
                switchOffOthers(binding.card4, binding.text4, binding.img4)
            }
            R.id.card3 -> {
                languageCardClick("german", binding.card3, binding.text3, binding.img3)
                switchOffOthers(binding.card2, binding.text2, binding.img2)
                switchOffOthers(binding.card1, binding.text1, binding.img1)
                switchOffOthers(binding.card4, binding.text4, binding.img4)
            }
            R.id.card4 -> {
                languageCardClick("spanish", binding.card4, binding.text4, binding.img4)
                switchOffOthers(binding.card2, binding.text2, binding.img2)
                switchOffOthers(binding.card3, binding.text3, binding.img3)
                switchOffOthers(binding.card1, binding.text1, binding.img1)
            }
        }
    }

    private fun setCardBackground(value: String, cardView: CardView, textView: TextView, imageView: ImageView) {
        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
        val textWhite = ContextCompat.getColor(requireContext(), R.color.white)

        if (mySharedPreference?.getPreferences("key") != null) {
            if (mySharedPreference?.getPreferences("key") == value) {
                cardView.setCardBackgroundColor(nuntiumColor)
                textView.setTextColor(textWhite)
                imageView.visibility = View.VISIBLE
            }
        }
    }

    private fun switchOffOthers(cardView: CardView, textView: TextView, imageView: ImageView) {
        val cardColor = ContextCompat.getColor(requireContext(), R.color.search_card_color)
        val textColor = ContextCompat.getColor(requireContext(), R.color.card_text_color)

        cardView.setCardBackgroundColor(cardColor)
        textView.setTextColor(textColor)
        imageView.visibility = View.GONE
    }

    private fun languageCardClick(
        value: String,
        cardView: CardView,
        textView: TextView,
        imageView: ImageView
    ) {
        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
        val textWhite = ContextCompat.getColor(requireContext(), R.color.white)
        cardView.setCardBackgroundColor(nuntiumColor)
        if (mySharedPreference?.getPreferences("key") != null) {
            if (mySharedPreference?.getPreferences("key") != value) {
                mySharedPreference?.setPreferences("key", value)
                cardView.setCardBackgroundColor(nuntiumColor)
                textView.setTextColor(textWhite)
                imageView.visibility = View.VISIBLE
            }
        } else {
            mySharedPreference?.setPreferences("key", value)
            cardView.setCardBackgroundColor(nuntiumColor)
            textView.setTextColor(textWhite)
            imageView.visibility = View.VISIBLE
        }
    }
}