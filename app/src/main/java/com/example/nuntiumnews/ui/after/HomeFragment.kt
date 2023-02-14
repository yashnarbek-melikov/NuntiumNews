package com.example.nuntiumnews.ui.after

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.recyclerview.HomeRecyclerAdapter
import com.example.nuntiumnews.databinding.FragmentHomeBinding
import com.example.nuntiumnews.databinding.ItemTabBinding
import com.example.nuntiumnews.models.newsModel.Article
import com.example.nuntiumnews.models.newsModel.Headlines
import com.example.nuntiumnews.repository.NewsRepository
import com.example.nuntiumnews.retrofit.ApiClient
import com.example.nuntiumnews.utils.NewsResource
import com.example.nuntiumnews.viewmodels.NewsViewModel
import com.example.nuntiumnews.viewmodels.NewsViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(R.layout.fragment_home), CoroutineScope {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var tabList: ArrayList<String>
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private lateinit var job: Job
    private var isCreate = false
    val map = HashMap<String, Headlines>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTabList()
        isCreate = true

        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(NewsRepository(ApiClient.apiService))
        )[NewsViewModel::class.java]

        homeRecyclerAdapter = HomeRecyclerAdapter(object : HomeRecyclerAdapter.OnClickListener {
            override fun onImageClick(article: Article) {
                val bundle = Bundle()
                bundle.putSerializable("article", article)
                findNavController().navigate(
                    R.id.action_homeNavigationFragment_to_articleFragment,
                    bundle
                )
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = Job()

        if (isCreate) {
            getViewModelInformation("sport")
            isCreate = false
        }

        binding.apply {
            recyclerView.adapter = homeRecyclerAdapter
            readyTabs()
        }

//        if(isCreate) {
//            getViewModelInformation("sport")
//        }
//        else {
//            getViewModelInformation(category?.let { tabList?.get(it) } ?: "sport")
//        }
    }

    private fun readyTabs() {

        tabList.forEach {
            binding.tabLayout.newTab().setText(it)
                .let { it1 -> binding.tabLayout.addTab(it1) }
        }

        tabList.forEachIndexed { index, s ->
            val itemTabBinding = ItemTabBinding.inflate(layoutInflater)
            itemTabBinding.tv.text = s
            if (index == 0) {
                itemTabBinding.tv.setTextColor(Color.WHITE)
                itemTabBinding.card.setCardBackgroundColor(Color.parseColor("#475AD7"))
            } else {
                itemTabBinding.tv.setTextColor(Color.parseColor("#7C82A1"))
                itemTabBinding.card.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
            }
            binding.tabLayout.getTabAt(index)?.customView = itemTabBinding.root
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val itemTabBinding = ItemTabBinding.bind(tab?.customView!!)
                itemTabBinding.tv.setTextColor(Color.WHITE)
                itemTabBinding.card.setCardBackgroundColor(Color.parseColor("#475AD7"))
                Log.d("LLASDLALSEFS", "onTabSelected: $map")
                if(!map.containsKey(tabList[tab.position])) {
                    getViewModelInformation(tabList[tab.position])
                } else {
                    homeRecyclerAdapter.submitList(map[tabList[tab.position]]?.articles)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val itemTabBinding = ItemTabBinding.bind(tab?.customView!!)
                itemTabBinding.tv.setTextColor(Color.parseColor("#7C82A1"))
                itemTabBinding.card.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun getTabList() {
        tabList = ArrayList()
        tabList.add("Sport")
        tabList.add("Politics")
        tabList.add("Life")
        tabList.add("Gaming")
        tabList.add("Animal")
        tabList.add("Nature")
        tabList.add("Food")
        tabList.add("Art")
        tabList.add("History")
        tabList.add("Fashion")

    }

    private fun getViewModelInformation(category: String) {
        lifecycleScope.launch {
            newsViewModel.getNews(category).collect {
                when (it) {
                    is NewsResource.Loading -> {
                    }

                    is NewsResource.Error -> {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    }

                    is NewsResource.Success -> {
                        it.headlines?.let { it1 -> map.put(category, it1) }
                        homeRecyclerAdapter.submitList(it.headlines?.articles)
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}