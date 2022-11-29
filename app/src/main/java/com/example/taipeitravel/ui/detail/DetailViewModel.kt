package com.example.taipeitravel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitravel.model.TravelData

class DetailViewModel : ViewModel() {

    var viewDataDetail = MutableLiveData<TravelData.ViewData>()

    fun getDetailImages(viewImage: List<TravelData.ViewData.ViewImage>): ArrayList<String> {
        val imageList = ArrayList<String>()
        if (viewImage.isNotEmpty()) {
            for (imageItem in viewImage) {
                imageList.add(imageItem.src!!)
            }
        }
        return imageList
    }

    fun sortMonths(months: String): String {
        val allMonths = months.split(",").toTypedArray()
        allMonths.sort()
        var returnMonths = ""
        allMonths.forEach {
            returnMonths += "$it,"
        }
        returnMonths.substring(0, returnMonths.length - 1)
        return returnMonths
    }

    fun observeViewDataDetail(): LiveData<TravelData.ViewData>{
        return viewDataDetail
    }

}