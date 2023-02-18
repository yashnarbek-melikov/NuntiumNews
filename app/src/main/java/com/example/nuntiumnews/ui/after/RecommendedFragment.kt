package com.example.nuntiumnews.ui.after

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.recyclerview.RecommendedRecyclerAdapter
import com.example.nuntiumnews.databinding.FragmentRecommendedBinding
import com.example.nuntiumnews.models.newsModel.Article
import com.example.nuntiumnews.repository.NewsRepository
import com.example.nuntiumnews.retrofit.ApiClient
import com.example.nuntiumnews.utils.MySharedPreference
import com.example.nuntiumnews.utils.NewsResource
import com.example.nuntiumnews.viewmodels.RecommendedViewModel
import com.example.nuntiumnews.viewmodels.RecommendedViewModelFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RecommendedFragment : Fragment(R.layout.fragment_recommended), CoroutineScope {
    private lateinit var job: Job
    private var _binding: FragmentRecommendedBinding? = null
    private lateinit var recommendedRecyclerAdapter: RecommendedRecyclerAdapter

    private lateinit var shuffleList: ArrayList<Article>
    private lateinit var recommendedList: ArrayList<String>
    private lateinit var recommendedViewModel: RecommendedViewModel
    private lateinit var mySharedPreference: MySharedPreference

    private var isCreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCreate = true
        shuffleList = ArrayList()
        recommendedList = ArrayList()

        mySharedPreference = MySharedPreference(requireContext())

        recommendedViewModel = ViewModelProvider(
            this,
            RecommendedViewModelFactory(NewsRepository(ApiClient.apiService))
        )[RecommendedViewModel::class.java]

        recommendedRecyclerAdapter =
            RecommendedRecyclerAdapter(
                shuffleList,
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

        val binding = FragmentRecommendedBinding.bind(view)
        _binding = binding
        job = Job()

        mySharedPreference.getPreferences("sports")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("life")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("animals")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("food")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("history")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("politics")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("gaming")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("nature")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("art")?.let { recommendedList.add(it) }
        mySharedPreference.getPreferences("fashion")?.let { recommendedList.add(it) }

        if (isCreate) {
            loadUi(recommendedList)
            isCreate = false
        }

        binding.swipe.setOnRefreshListener {
            shuffleList.clear()
            loadUi(recommendedList)
        }

        binding.recyclerView.adapter = recommendedRecyclerAdapter
    }

    private fun loadUi(list: ArrayList<String>) {
        lifecycleScope.launch(Dispatchers.Main) {
            recommendedViewModel.getRecommendedNews(list).collect {
                when (it) {
                    is NewsResource.Loading -> {
                        _binding?.shimmerLayout?.visibility = View.VISIBLE
                        _binding?.shimmerLayout?.startShimmer()
                        _binding?.errorText?.visibility = View.GONE
                        _binding?.recyclerView?.visibility = View.GONE
                    }
                    is NewsResource.Error -> {
                        _binding?.swipe?.isRefreshing = false
                        _binding?.shimmerLayout?.stopShimmer()
                        _binding?.shimmerLayout?.visibility = View.GONE
                        _binding?.recyclerView?.visibility = View.GONE
                        _binding?.errorText?.visibility = View.VISIBLE
                        _binding?.errorText?.text = it.message
                    }
                    is NewsResource.Success -> {
                        shuffleList.addAll(it.headlines?.articles as ArrayList<Article>)
                        lifecycleScope.launch(Dispatchers.Default) {
                            shuffleList.shuffle()
                        }
                        if (shuffleList.size == recommendedList.size * 20) {
                            recommendedRecyclerAdapter.notifyDataSetChanged()
                            _binding?.swipe?.isRefreshing = false
                            _binding?.errorText?.visibility = View.GONE
                            _binding?.shimmerLayout?.stopShimmer()
                            _binding?.shimmerLayout?.visibility = View.GONE
                            _binding?.recyclerView?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        recommendedList.clear()
        _binding = null
    }
}