package com.example.taipeitravel.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitravel.databinding.FragmentHomeBinding
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.HomeListClickListener

class HomeFragment : Fragment(), HomeListClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter(this)
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(binding.root.context, 1)
            adapter = homeAdapter
        }
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getTravelData()
        viewModel.observeViewDataLiveData().observe(viewLifecycleOwner) {
            homeAdapter.setViewDataList(it)
        }

    }

    override fun onHomeListClickListener(viewData: TravelData.ViewData) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(viewData)
        findNavController().navigate(action)
    }

}