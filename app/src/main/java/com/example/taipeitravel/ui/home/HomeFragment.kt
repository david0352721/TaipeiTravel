package com.example.taipeitravel.ui.home

import android.content.Context
import android.content.SharedPreferences
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
import com.example.taipeitravel.utility.LocaleHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment(), HomeListClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var preferences: SharedPreferences

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

        homeAdapter = HomeAdapter(this)
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(binding.root.context, 1)
            adapter = homeAdapter
        }
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getTravelData(apiLang)
        viewModel.observeViewDataLiveData().observe(viewLifecycleOwner) {
            homeAdapter.setViewDataList(it)
            if (it.size > 30) {
                binding.homeNextBT.isVisible = false
            }
        }
//        viewModel.observeLangLiveData().observe(viewLifecycleOwner) {
//            Log.d("Home", "lang: $it")
//        }
        viewModel.observePageCountLiveData().observe(viewLifecycleOwner) {
            Log.d("Home", "page: $it")
            binding.homePrevBT.isVisible = it != 1
        }

        binding.homePrevBT.setOnClickListener {
            viewModel.getPrevList(apiLang)
        }

        binding.homeNextBT.setOnClickListener {
            viewModel.getNextList(apiLang)
        }

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
                                        LocaleHelper().setLocale(requireContext(), "zh", "TW")
                                        requireActivity().recreate()
                                        viewModel.changeLangList("zh-tw")
                                        dialog.dismiss()
                                    }
                                    1 -> {
                                        LocaleHelper().setLocale(requireContext(), "en", "US")
                                        requireActivity().recreate()
                                        viewModel.changeLangList("en")
                                        dialog.dismiss()
                                    }
                                    2 -> {
                                        LocaleHelper().setLocale(requireContext(), "ja", "JP")
                                        requireActivity().recreate()
                                        viewModel.changeLangList("ja")
                                        dialog.dismiss()
                                    }
                                }
                            }
                            .show()
                        true
                    }
//                    R.id.lang_tw -> {
//                        LocaleHelper().setLocale(requireContext(), "zh", "TW")
//                        requireActivity().recreate()
//                        viewModel.changeLangList("zh-tw")
//                        true
//                    }
//                    R.id.lang_en -> {
//                        LocaleHelper().setLocale(requireContext(), "en", "US")
//                        requireActivity().recreate()
//                        viewModel.changeLangList("en")
//                        true
//                    }
//                    R.id.lang_jp -> {
//                        LocaleHelper().setLocale(requireContext(), "ja", "JP")
//                        requireActivity().recreate()
//                        viewModel.changeLangList("ja")
//                        true
//                    }
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