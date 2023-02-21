package com.example.nuntiumnews.ui.after

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.App
import com.example.nuntiumnews.R
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.FragmentArticleBinding
import com.example.nuntiumnews.utils.setImage
import com.example.nuntiumnews.utils.textToDate
import com.example.nuntiumnews.viewmodels.SaveViewModel
import javax.inject.Inject

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private var _binding: FragmentArticleBinding? = null
    private lateinit var article: Article
    private var isLiked = 0

    @Inject
    lateinit var saveViewModel: SaveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        arguments.let {
            article = it?.getSerializable("article") as Article
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArticleBinding.bind(view)
        _binding = binding

        isLiked = saveViewModel.getIsLiked(article.publishedAt)

        if (isLiked == 1) {
            binding.saveImg.setImageResource(R.drawable.ic_saved_white)
            binding.saveImg.setAltImageResource(R.drawable.ic_saved_gray)
        } else {
            binding.saveImg.setImageResource(R.drawable.ic_save_white)
            binding.saveImg.setAltImageResource(R.drawable.ic_save)
        }

        binding.saveImg.setOnClickListener {
            isLiked = if (isLiked == 0) {
                binding.saveImg.setImageResource(R.drawable.ic_saved_white)
                binding.saveImg.setAltImageResource(R.drawable.ic_saved_gray)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                saveViewModel.addArticle(article)
                1
            } else {
                binding.saveImg.setImageResource(R.drawable.ic_save_white)
                binding.saveImg.setAltImageResource(R.drawable.ic_save)
                Toast.makeText(context, "UnSaved", Toast.LENGTH_SHORT).show()
                saveViewModel.deleteArticle(article.publishedAt)
                0
            }
        }

        binding.newsDataTv.text = article.description
        binding.sourceNameTv.text = textToDate(article.publishedAt)
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