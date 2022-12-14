package com.example.taipeitravel.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.taipeitravel.MainActivity
import com.example.taipeitravel.databinding.FragmentDetailBinding
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.DetailCategoryClickListener
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class DetailFragment : Fragment(), DetailCategoryClickListener {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var imageList = ArrayList<String>()
    private lateinit var detailCategoryAdapter: DetailCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        detailCategoryAdapter = DetailCategoryAdapter(this)
        binding.detailCategoryContentRecyclerView.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = detailCategoryAdapter
        }

        val viewData = args.clickViewData
        viewModel.viewDataDetail.value = viewData
        imageList = viewModel.getDetailImages(viewData.images!!)
        viewModel.setCategoryList(viewData.category!!)
        viewModel.observeViewDataDetail().observe(viewLifecycleOwner) {
            binding.detailBanner.setAdapter(object : BannerImageAdapter<TravelData.ViewData.ViewImage?>(viewData.images) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: TravelData.ViewData.ViewImage?,
                    position: Int,
                    size: Int
                ) {
                    //????????????????????????
                    Glide.with(holder!!.itemView)
                        .load(data!!.src)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into(holder.imageView)
                }
            })
                .addBannerLifecycleObserver(this)
                .indicator = CircleIndicator(binding.root.context)
            (requireActivity() as MainActivity).supportActionBar?.title = viewData.name

            binding.detailIntroduction.text = viewData.introduction
            binding.detailAddressContent.text = viewData.address
            binding.detailMtelContent.text = viewData.tel
            binding.detailOpenMonthContent.text = viewModel.sortMonths(viewData.months.toString())

            viewModel.observeViewCategoryList().observe(viewLifecycleOwner) {
                detailCategoryAdapter.setViewCategoryList(it)
            }

            binding.detailTargetContent.text = viewModel.getTargetList(viewData.target!!)
            binding.detailOfficialSiteContent.paintFlags = binding.detailOfficialSiteContent.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.detailOfficialSiteContent.text = viewData.official_site
            binding.detailUrlContent.paintFlags = binding.detailUrlContent.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.detailUrlContent.text = viewData.url
            binding.detailLastUpdateContent.text = viewData.modified
            binding.detailOpenMap.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${viewData.nlat}, ${viewData.elong}?q=${viewData.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

            if (viewData.official_site.isNullOrEmpty()) {
                binding.detailOfficialSiteContent.isVisible = false
            }

            binding.detailOfficialSiteContent.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToWebFragment(viewData.official_site)
                findNavController().navigate(action)
            }

            binding.detailUrlContent.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToWebFragment(viewData.url)
                findNavController().navigate(action)
            }

        }

    }

    override fun onDetailCategoryClickListener(categoryId: String) {
        Log.d("Detail", "click category: $categoryId")
        val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment().setCategoryId(categoryId)
        findNavController().navigate(action)
    }

}