package com.example.taipeitravel.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.TravelInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var viewDataLiveData = MutableLiveData<List<TravelData.ViewData>>()
    private var pageCountLiveData = MutableLiveData<Int>(1)
    private var langLiveData = MutableLiveData<String>("zh-tw")

    fun getTravelData() {
        Log.d("Home", "get Data lang:${langLiveData.value}, count: ${pageCountLiveData.value}")
        TravelInstance.api.getAllViews(langLiveData.value.toString(), pageCountLiveData.value!!.toInt()).enqueue(object : Callback<TravelData>{
            override fun onResponse(call: Call<TravelData>, response: Response<TravelData>) {
                if (response.body() != null) {
                    viewDataLiveData.value = response.body()!!.data!!
                }
            }

            override fun onFailure(call: Call<TravelData>, t: Throwable) {
                Log.d("Home", "error: ${t.message.toString()}")
            }

        })
    }

    fun changeLangList(changeLang: String) {
        langLiveData.value = changeLang
        getTravelData()
    }

    fun getPrevList() {
        pageCountLiveData.value = pageCountLiveData.value?.minus(1)
        getTravelData()
    }

    fun getNextList() {
        pageCountLiveData.value = pageCountLiveData.value?.plus(1)
        getTravelData()
    }

    fun observePageCountLiveData(): LiveData<Int> {
        if (pageCountLiveData.value.toString().isEmpty()) {
            pageCountLiveData.value = 1
        }
        return pageCountLiveData
    }

    fun observeLangLiveData(): LiveData<String> {
        return langLiveData
    }

    fun observeViewDataLiveData(): LiveData<List<TravelData.ViewData>> {
        return viewDataLiveData
    }

}