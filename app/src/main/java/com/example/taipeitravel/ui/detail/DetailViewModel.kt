package com.example.taipeitravel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitravel.model.TravelData

class DetailViewModel : ViewModel() {

    var viewDataDetail = MutableLiveData<TravelData.ViewData>()
    private var viewCategoryList = MutableLiveData<List<TravelData.ViewData.ViewCategory>>()

    fun getDetailImages(viewImage: List<TravelData.ViewData.ViewImage?>): ArrayList<String> {
        val imageList = ArrayList<String>()
        if (viewImage.isNotEmpty()) {
            for (imageItem in viewImage) {
                imageList.add(imageItem?.src!!)
            }
        }
        return imageList
    }

    fun setCategoryList(viewCategories: List<TravelData.ViewData.ViewCategory>) {
        viewCategoryList.value = viewCategories
    }

//    fun getCategoryList(viewCategory: List<TravelData.ViewData.ViewCategory>): String {
//        var categoryString = ""
//        if (viewCategory.isNotEmpty()) {
//            for (item in viewCategory) {
//                categoryString += "${item.name},"
//            }
//        }
//        return categoryString.substring(0, categoryString.length - 1)
//    }

    fun getTargetList(viewTarget: List<TravelData.ViewData.ViewTarget?>): String {
        var targetString = ""
        if (viewTarget.isNotEmpty()) {
            for (item in viewTarget) {
                targetString += "${item?.name},"
            }
            return targetString.substring(0, targetString.length - 1)
        }
        return targetString
    }

    fun sortMonths(months: String): String {
        val allMonths = months.split(",").toTypedArray()
        allMonths.sort()
        var monthString = ""
        allMonths.forEach {
            monthString += "$it,"
        }
        return monthString.substring(0, monthString.length - 1)
    }

    fun observeViewDataDetail(): LiveData<TravelData.ViewData>{
        return viewDataDetail
    }

    fun observeViewCategoryList(): LiveData<List<TravelData.ViewData.ViewCategory>> {
        return viewCategoryList
    }

}