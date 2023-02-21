package com.example.nuntiumnews.ui.after

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nuntiumnews.App
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.recyclerview.HomeRecyclerAdapter
import com.example.nuntiumnews.database.entity.Article
import com.example.nuntiumnews.databinding.FragmentHomeBinding
import com.example.nuntiumnews.databinding.ItemTabBinding
import com.example.nuntiumnews.utils.NewsResource
import com.example.nuntiumnews.viewmodels.NewsViewModel
import com.example.nuntiumnews.viewmodels.SaveViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(R.layout.fragment_home), CoroutineScope {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var tabList: ArrayList<String>
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter

    @Inject
    lateinit var newsViewModel: NewsViewModel

    @Inject
    lateinit var savedViewModel: SaveViewModel

    private lateinit var list: ArrayList<Article>
    private lateinit var job: Job
    private var isCreate = false
    private var tabPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        getTabList()
        isCreate = true

        list = ArrayList()

        homeRecyclerAdapter =
            HomeRecyclerAdapter(
                savedViewModel,
                list,
                requireContext(),
                object : HomeRecyclerAdapter.OnClickListener {
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

        val binding = FragmentHomeBinding.bind(view)
        _binding = binding

        job = Job()

        if (isCreate) {
            getViewModelInformation(binding, "sports")
            isCreate = false
        }

        binding.swipe.setOnRefreshListener {
            getViewModelInformation(binding, tabList[binding.tabLayout.selectedTabPosition])
        }

        binding.searchEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text.isEmpty()) {
                    Toast.makeText(requireContext(), "enter text", Toast.LENGTH_SHORT).show()
                } else {
                    val bundle = Bundle()
                    bundle.putString("text", textView.text.toString())
                    findNavController().navigate(
                        R.id.action_homeNavigationFragment_to_searchFragment,
                        bundle
                    )
                    binding.searchEt.setText("")
                }
            }
            return@setOnEditorActionListener true
        }

        binding.apply {
            recyclerView.adapter = homeRecyclerAdapter
            readyTabs(binding, tabPosition)
        }
    }

    private fun readyTabs(binding: FragmentHomeBinding, tabPosition: Int) {

        tabList.forEach {
            binding.tabLayout.newTab().setText(it)
                .let { it1 -> binding.tabLayout.addTab(it1) }
        }

        tabList.forEachIndexed { index, s ->
            val itemTabBinding = ItemTabBinding.inflate(layoutInflater)
            itemTabBinding.tv.text = s
            if (index == tabPosition) {
                val tab = binding.tabLayout.getTabAt(index)
                tab?.select()
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
                binding.id.smoothScrollTo(0, 0)
                getViewModelInformation(binding, tabList[tab.position])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val itemTabBinding = ItemTabBinding.bind(tab?.customView!!)
                itemTabBinding.tv.setTextColor(Color.parseColor("#7C82A1"))
                itemTabBinding.card.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        val tabStart = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(0)
        val tabStartParams = tabStart.layoutParams as ViewGroup.MarginLayoutParams
        tabStartParams.setMargins(16, 0, 0, 0)
        tabStart.requestLayout()

        val tabEnd =
            (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(binding.tabLayout.tabCount - 1)
        val tabEndParams = tabEnd.layoutParams as ViewGroup.MarginLayoutParams
        tabEndParams.setMargins(0, 0, 45, 0)
        tabEnd.requestLayout()
    }

    private fun getTabList() {
        tabList = ArrayList()
        tabList.add(getString(R.string.sports_cat))
        tabList.add(getString(R.string.politics_cat))
        tabList.add(getString(R.string.life_cat))
        tabList.add(getString(R.string.gaming_cat))
        tabList.add(getString(R.string.animals_cat))
        tabList.add(getString(R.string.nature_cat))
        tabList.add(getString(R.string.food_cat))
        tabList.add(getString(R.string.art_cat))
        tabList.add(getString(R.string.history_cat))
        tabList.add(getString(R.string.fashion_cat))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabPosition = _binding?.tabLayout?.selectedTabPosition ?: 0
        job.cancel()
        _binding = null
    }

    private fun getViewModelInformation(binding: FragmentHomeBinding, category: String) {
        lifecycleScope.launch {
            newsViewModel.getNews(category).collect {
                when (it) {
                    is NewsResource.Loading -> {
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.startShimmer()
                        binding.errorText.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                    }

                    is NewsResource.Error -> {
                        binding.swipe.isRefreshing = false
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                        binding.errorText.text = it.message
                    }

                    is NewsResource.Success -> {
                        binding.swipe.isRefreshing = false
                        binding.errorText.visibility = View.GONE
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        homeRecyclerAdapter.list = it.headlines?.articles as ArrayList<Article>
                        homeRecyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}