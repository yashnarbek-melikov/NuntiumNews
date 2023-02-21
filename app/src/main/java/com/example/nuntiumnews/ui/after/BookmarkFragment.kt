package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.App
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.recyclerview.RecommendedRecyclerAdapter
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.FragmentBookmarkBinding
import com.example.nuntiumnews.viewmodels.SaveViewModel
import javax.inject.Inject

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var savedRecyclerAdapter: RecommendedRecyclerAdapter

    @Inject
    lateinit var saveViewModel: SaveViewModel
    private lateinit var savedList: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        savedList = ArrayList()

        savedRecyclerAdapter = RecommendedRecyclerAdapter(
            savedList,
            object : RecommendedRecyclerAdapter.OnClickListener {
                override fun onImageClick(newsModel: Article) {
                    val bundle = Bundle()
                    bundle.putSerializable("article", newsModel)
                    findNavController().navigate(
                        R.id.action_homeNavigationFragment_to_articleFragment,
                        bundle
                    )
                }
            })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookmarkBinding.bind(view)
        _binding = binding
        savedList.addAll(saveViewModel.getAllArticle() as ArrayList<Article>)
        binding.rv.adapter = savedRecyclerAdapter

        if(savedList.isEmpty()) {
            binding.card.visibility = View.VISIBLE
            binding.bookmarkImg.visibility = View.VISIBLE
            binding.text1.visibility = View.VISIBLE
            binding.text2.visibility = View.VISIBLE
            binding.text3.visibility = View.VISIBLE
            binding.rv.visibility = View.GONE
        } else {
            binding.card.visibility = View.GONE
            binding.bookmarkImg.visibility = View.GONE
            binding.text1.visibility = View.GONE
            binding.text2.visibility = View.GONE
            binding.text3.visibility = View.GONE
            savedRecyclerAdapter.notifyDataSetChanged()
            binding.rv.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        savedList.clear()
        savedList.addAll(saveViewModel.getAllArticle() as ArrayList<Article>)
        savedRecyclerAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}