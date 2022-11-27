package com.example.taipeitravel.ui.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.utility.showDialog

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

    fun observeViewDataDetail(): LiveData<TravelData.ViewData>{
        return viewDataDetail
    }

}