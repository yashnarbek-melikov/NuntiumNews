package com.example.nuntiumnews.ui.after

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentCategoriesBinding
import com.example.nuntiumnews.utils.MySharedPreference

class CategoriesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPreference: MySharedPreference
    private lateinit var cachedMap: HashMap<String, String>
    private lateinit var removeList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        cachedMap = HashMap()
        removeList = ArrayList()
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

        binding.buttonChange.setOnClickListener {
            if (cachedMap.size > 0) {
                for (mutableEntry in cachedMap) {
                    mySharedPreference.setPreferences(mutableEntry.key, mutableEntry.value)
                }
                removeList.forEach {
                    mySharedPreference.setPreferences(it, null)
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Choose Topic", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backCard.setOnClickListener {
            findNavController().popBackStack()
        }

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
        val textWhite = ContextCompat.getColor(requireContext(), R.color.card_background_color)

        if (mySharedPreference.getPreferences(key) != null) {
            if (mySharedPreference.getPreferences(key) == value) {
                cachedMap[key] = value
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
        val textWhite = ContextCompat.getColor(requireContext(), R.color.card_background_color)

        if (cachedMap[key] == value) {
            cachedMap.remove(key, value)
            removeList.add(key)
            cardView.setCardBackgroundColor(cardColor)
            textView.setTextColor(textColor)
        } else {
            cachedMap[key] = value
            removeList.remove(key)
            cardView.setCardBackgroundColor(nuntiumColor)
            textView.setTextColor(textWhite)
        }
    }
}