package com.example.taipeitravel.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitravel.R
import com.example.taipeitravel.databinding.FragmentHomeBinding
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.HomeListClickListener
import com.example.taipeitravel.utility.LocaleHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment(), HomeListClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var preferences: SharedPreferences
    private val args: HomeFragmentArgs by navArgs()
    private var categoryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val apiLang = preferences.getString("apiLang", "zh-tw").toString()
        var maxPage = 0
        var nowPage = 0

        homeAdapter = HomeAdapter(this)
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(binding.root.context, 1)
            adapter = homeAdapter
        }
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        if (args.categoryId != null) {
            categoryId = args.categoryId
            viewModel.setCategoryId(categoryId!!)
        }

        viewModel.getTravelData(apiLang)
        viewModel.observeViewDataArray().observe(viewLifecycleOwner) {
            homeAdapter.setViewDataList(it)
            binding.homeProgressBar.isVisible = false
        }

        viewModel.observePageCountLiveData().observe(viewLifecycleOwner) {
            nowPage = it
        }

        viewModel.observeMaxPageCount().observe(viewLifecycleOwner) {
            maxPage = it!!
        }

        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(requireContext(), resources.getString(R.string.home_recyclerViewLast), Toast.LENGTH_LONG).show()
                    if (maxPage <= nowPage) {
                        Toast.makeText(requireContext(), resources.getString(R.string.home_recyclerViewLast), Toast.LENGTH_LONG).show()
                    } else {
                        binding.homeProgressBar.isVisible = true
                        viewModel.loadMore(apiLang)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.menu_changeLang -> {
                        val languageItems: Array<String> = resources.getStringArray(R.array.choseLanguages)
                        MaterialAlertDialogBuilder(requireContext())
                            .setItems(languageItems) { dialog, which ->
                                when (which) {
                                    0 -> {
                                        viewModel.removeAll()
                                        LocaleHelper().setLocale(requireContext(), "zh", "TW")
                                        viewModel.changeLangList("zh-tw")
                                        requireActivity().recreate()
                                        dialog.dismiss()
                                    }
                                    1 -> {
                                        viewModel.removeAll()
                                        LocaleHelper().setLocale(requireContext(), "en", "US")
                                        viewModel.changeLangList("en")
                                        requireActivity().recreate()
                                        dialog.dismiss()
                                    }
                                    2 -> {
                                        viewModel.removeAll()
                                        LocaleHelper().setLocale(requireContext(), "ja", "JP")
                                        viewModel.changeLangList("ja")
                                        requireActivity().recreate()
                                        dialog.dismiss()
                                    }
                                }
                            }
                            .show()
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