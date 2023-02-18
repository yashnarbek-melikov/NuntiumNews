package com.example.nuntiumnews.ui.before

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.PageTransformer
import com.example.nuntiumnews.R
import com.example.nuntiumnews.adapters.itemdecoration.HorizontalMarginItemDecoration
import com.example.nuntiumnews.adapters.viewpager.OnBoardingAdapter
import com.example.nuntiumnews.databinding.FragmentOnBoardingBinding
import com.example.nuntiumnews.models.OnBoardingItems
import kotlin.math.abs

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private var list: ArrayList<OnBoardingItems>? = null
    private var onPause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        addImages()

        binding.viewPager2.adapter = list?.let { OnBoardingAdapter(it) }
        binding.viewPager2.offscreenPageLimit = 1

        if (onPause) {
            binding.select3.setImageResource(R.drawable.ic_select)
            binding.select2.setImageResource(R.drawable.ic_unselected)
            binding.select1.setImageResource(R.drawable.ic_unselected)
        }

        binding.buttonNext.setOnClickListener {
            if (binding.viewPager2.currentItem != 2) {
                binding.viewPager2.currentItem += 1
            } else {
                findNavController().navigate(R.id.action_onBoardingFragment_to_welcomeScreenFragment)
            }
        }
        registerOnPageChange()

        binding.viewPager2.setPageTransformer(pageTransformer())


        itemDecoration()

        return binding.root
    }

    private fun pageTransformer(): PageTransformer {
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.15f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        return pageTransformer
    }

    private fun itemDecoration() {
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPager2.addItemDecoration(itemDecoration)
    }

    private fun registerOnPageChange() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val fadeIn: Animation = AlphaAnimation(0f, 1f)
                fadeIn.interpolator = DecelerateInterpolator()
                fadeIn.duration = 700
                super.onPageSelected(position)
                when (position) {
                    1 -> {
                        binding.nextTv.text = "Next"
                        binding.select1.setImageResource(R.drawable.ic_unselected)
                        binding.select2.setImageResource(R.drawable.ic_select)
                        binding.select2.animation = fadeIn
                        binding.select3.setImageResource(R.drawable.ic_unselected)
                        binding.title.text = "First to know"
                        binding.desc1.text = getString(R.string.page1_desc_1)
                        binding.desc2.text = getString(R.string.page1_desc_2)
                        binding.title.animation = fadeIn
                        binding.desc1.animation = fadeIn
                        binding.desc2.animation = fadeIn
                    }
                    2 -> {
                        binding.nextTv.text = "Go"
                        binding.select1.setImageResource(R.drawable.ic_unselected)
                        binding.select2.setImageResource(R.drawable.ic_unselected)
                        binding.select3.setImageResource(R.drawable.ic_select)
                        binding.select3.animation = fadeIn
                        binding.title.text = "Smart"
                        binding.desc1.text = getString(R.string.page2_desc_1)
                        binding.desc2.text = getString(R.string.page2_desc_2)
                        binding.title.animation = fadeIn
                        binding.desc1.animation = fadeIn
                        binding.desc2.animation = fadeIn
                    }
                    else -> {
                        binding.nextTv.text = "Next"
                        binding.select1.setImageResource(R.drawable.ic_select)
                        binding.select1.animation = fadeIn
                        binding.select2.setImageResource(R.drawable.ic_unselected)
                        binding.select3.setImageResource(R.drawable.ic_unselected)
                        binding.title.text = getString(R.string.boarding_title)
                        binding.desc1.text = getString(R.string.page3_desc_1)
                        binding.desc2.text = getString(R.string.page3_desc_2)
                        binding.title.animation = fadeIn
                        binding.desc1.animation = fadeIn
                        binding.desc2.animation = fadeIn
                    }
                }
            }
        })
    }

    private fun addImages() {
        list = ArrayList()

        list?.add(OnBoardingItems(R.drawable.first_image))
        list?.add(OnBoardingItems(R.drawable.second_image))
        list?.add(OnBoardingItems(R.drawable.third_image))
    }

    override fun onPause() {
        super.onPause()
        onPause = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        list = null
        _binding = null
    }
}