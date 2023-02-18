package com.example.nuntiumnews.ui.after

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.databinding.FragmentArticleBinding
import com.example.nuntiumnews.models.newsModel.Article
import com.example.nuntiumnews.utils.setImage
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            article = it?.getSerializable("article") as Article
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsDataTv.text = article.description
        binding.sourceNameTv.text = article.source?.name
        binding.titleTv.text = article.title
        article.urlToImage?.let { binding.image.setImage(it) }
        article.urlToImage?.let { binding.imageOnBg.setImage(it) }

        binding.backImg.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.shareImg.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.description)
            shareIntent.putExtra(Intent.EXTRA_TITLE, article.title)
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}