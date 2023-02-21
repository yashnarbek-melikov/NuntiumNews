package com.example.nuntiumnews.ui.after

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.nuntiumnews.utils.hide
import com.example.nuntiumnews.utils.show
import com.google.android.material.card.MaterialCardView
import com.yariksoffice.lingver.Lingver
import java.util.*

class LanguageFragment : Fragment(R.layout.fragment_language){

    private var _binding: FragmentLanguageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLanguageBinding.bind(view)
        _binding = binding

        binding.backCard.setOnClickListener {
            findNavController().popBackStack()
        }
        var language = Lingver.getInstance().getLanguage()

        binding.apply {

            fun select(card: CardView, text: TextView, image: ImageView) {
                title.text = getString(R.string.language)
                text1.text = getString(R.string.english)
                text2.text = getString(R.string.turkish)
                text3.text = getString(R.string.german)
                text4.text = getString(R.string.russian)

                card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.nuntium_color))
                text.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_background_color))
                image.show()
            }

            when (language) {
                "ru" -> {
                    select(card4, text4, img4)
                }
                "de" -> {
                    select(card3, text3, img3)
                }
                "tr" -> {
                    select(card2, text2, img2)
                }
                else -> {
                    select(card1, text1, img1)
                }
            }

            fun clear() {
                val color1 = ContextCompat.getColor(requireContext(), R.color.search_card_color)
                card1.setCardBackgroundColor(color1)
                card2.setCardBackgroundColor(color1)
                card3.setCardBackgroundColor(color1)
                card4.setCardBackgroundColor(color1)
                val color2 = ContextCompat.getColor(requireContext(), R.color.card_text_color)
                text1.setTextColor(color2)
                text2.setTextColor(color2)
                text3.setTextColor(color2)
                text4.setTextColor(color2)

                img1.hide()
                img2.hide()
                img3.hide()
                img4.hide()
            }


            card1.setOnClickListener {
                if (language != "en") {
                    Lingver.getInstance().setLocale(requireContext(), "en")
                    language = "en"
                    clear()
                    select(card1, text1, img1)
                }
            }
            card4.setOnClickListener {
                if (language != "ru") {
                    language = "ru"
                    Lingver.getInstance().setLocale(requireContext(), "ru")
                    clear()
                    select(card4, text4, img4)
                }
            }
            card3.setOnClickListener {
                if (language != "de") {
                    language = "de"
                    Lingver.getInstance().setLocale(requireContext(), "de")
                    clear()
                    select(card3, text3, img3)
                }
            }
            card2.setOnClickListener {
                if (language != "tr") {
                    language = "tr"
                    Lingver.getInstance().setLocale(requireContext(), "tr")
                    clear()
                    select(card2, text2, img2)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun setCardBackground(value: String, cardView: CardView, textView: TextView, imageView: ImageView) {
//        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
//        val textWhite = ContextCompat.getColor(requireContext(), R.color.card_background_color)
//
//        if (mySharedPreference.getPreferences("key") != null) {
//            if (mySharedPreference.getPreferences("key") == value) {
//                cardView.setCardBackgroundColor(nuntiumColor)
//                textView.setTextColor(textWhite)
//                imageView.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun switchOffOthers(cardView: CardView, textView: TextView, imageView: ImageView) {
//        val cardColor = ContextCompat.getColor(requireContext(), R.color.search_card_color)
//        val textColor = ContextCompat.getColor(requireContext(), R.color.card_text_color)
//
//        cardView.setCardBackgroundColor(cardColor)
//        textView.setTextColor(textColor)
//        imageView.visibility = View.GONE
//    }
//
//    private fun languageCardClick(
//        value: String,
//        cardView: CardView,
//        textView: TextView,
//        imageView: ImageView
//    ) {
//        val nuntiumColor = ContextCompat.getColor(requireContext(), R.color.nuntium_color)
//        val textWhite = ContextCompat.getColor(requireContext(), R.color.card_background_color)
//        cardView.setCardBackgroundColor(nuntiumColor)
//        if (mySharedPreference.getPreferences("key") != null) {
//            if (mySharedPreference.getPreferences("key") != value) {
//                mySharedPreference.setPreferences("key", value)
//                cardView.setCardBackgroundColor(nuntiumColor)
//                textView.setTextColor(textWhite)
//                imageView.visibility = View.VISIBLE
//            }
//        } else {
//            mySharedPreference.setPreferences("key", value)
//            cardView.setCardBackgroundColor(nuntiumColor)
//            textView.setTextColor(textWhite)
//            imageView.visibility = View.VISIBLE
//        }
//    }
}