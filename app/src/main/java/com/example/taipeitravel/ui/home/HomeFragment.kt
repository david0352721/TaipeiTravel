package com.example.taipeitravel.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taipeitravel.R
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
            if (it.size > 30) {
                binding.homeNextBT.isVisible = false
            }
        }
        viewModel.observeLangLiveData().observe(viewLifecycleOwner) {
            Log.d("Home", "lang: $it")
        }
        viewModel.observePageCountLiveData().observe(viewLifecycleOwner) {
            Log.d("Home", "page: $it")
            binding.homePrevBT.isVisible = it != 1
        }

        binding.homePrevBT.setOnClickListener {
            viewModel.getPrevList()
        }

        binding.homeNextBT.setOnClickListener {
            viewModel.getNextList()
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.lang_tw -> {
                        viewModel.changeLangList("zh-tw")
                        true
                    }
                    R.id.lang_en -> {
                        viewModel.changeLangList("en")
                        true
                    }
                    R.id.lang_jp -> {
                        viewModel.changeLangList("ja")
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onHomeListClickListener(viewData: TravelData.ViewData) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(viewData)
        findNavController().navigate(action)
    }

}