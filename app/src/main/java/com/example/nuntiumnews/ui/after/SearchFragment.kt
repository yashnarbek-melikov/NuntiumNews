package com.example.nuntiumnews.ui.after

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.App
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.recyclerview.RecommendedRecyclerAdapter
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.FragmentSearchBinding
import com.example.nuntiumnews.utils.NewsResource
import com.example.nuntiumnews.viewmodels.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val ARG_PARAM1 = "text"

class SearchFragment : Fragment(R.layout.fragment_search), CoroutineScope {

    private var param1: String? = null
    private var _binding: FragmentSearchBinding? = null
    private lateinit var job: Job
    private lateinit var searchRecyclerAdapter: RecommendedRecyclerAdapter
    private lateinit var searchList: ArrayList<Article>
    private var isCreate = false

    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        isCreate = true
        searchList = ArrayList()

        searchRecyclerAdapter =
            RecommendedRecyclerAdapter(
                searchList,
                object : RecommendedRecyclerAdapter.OnClickListener {
                    override fun onImageClick(newsModel: Article) {
                        val bundle = Bundle()
                        bundle.putSerializable("article", newsModel)
                        findNavController().navigate(
                            R.id.action_searchFragment_to_articleFragment,
                            bundle
                        )
                    }

                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
        _binding = binding

        binding.searchEt.setText(param1)

        binding.searchEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text.isEmpty()) {
                    Toast.makeText(requireContext(), "enter text", Toast.LENGTH_SHORT).show()
                } else {
                    searchList.clear()
                    val searchText = textView.text.toString()
                    loadUi(searchText, binding)
                }
            }
            return@setOnEditorActionListener true
        }

        job = Job()
        binding.backCard.setOnClickListener {
            findNavController().popBackStack()
        }
        if (isCreate) {
            param1?.let { loadUi(it, binding) }
            isCreate = false
        }
        binding.recyclerView.adapter = searchRecyclerAdapter
    }

    fun loadUi(searchText: String, binding: FragmentSearchBinding) {
        lifecycleScope.launch(Dispatchers.Main) {
            searchText.let {
                searchViewModel.getSearch(it).collect {
                    when (it) {
                        is NewsResource.Loading -> {
                            binding.shimmerLayout.visibility = View.VISIBLE
                            binding.shimmerLayout.startShimmer()
                            binding.errorText.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                        }
                        is NewsResource.Error -> {
                            binding.shimmerLayout.stopShimmer()
                            binding.shimmerLayout.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.errorText.visibility = View.VISIBLE
                            binding.errorText.text = it.message
                        }
                        is NewsResource.Success -> {
                            searchList.addAll(it.headlines?.articles as ArrayList<Article>)
                            searchRecyclerAdapter.notifyDataSetChanged()
                            binding.errorText.visibility = View.GONE
                            binding.shimmerLayout.stopShimmer()
                            binding.shimmerLayout.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }
}