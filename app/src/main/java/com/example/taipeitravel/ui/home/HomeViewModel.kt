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
    private var viewDataArray = MutableLiveData<ArrayList<TravelData.ViewData>>()
    private var pageCountLiveData = MutableLiveData(1)
    private var categoryIdsLiveData = MutableLiveData<String?>()
    private var failConnectLiveData = MutableLiveData<String>()
    private var maxPageCountLiveData = MutableLiveData<Int?>()

    init {
        viewDataArray.value = arrayListOf()
    }

    fun getTravelData(apiLang: String) {
        TravelInstance.api.getAllViews(apiLang, categoryIdsLiveData.value, pageCountLiveData.value!!.toInt()).enqueue(object : Callback<TravelData>{
            override fun onResponse(call: Call<TravelData>, response: Response<TravelData>) {
                if (response.body() != null) {
                    if ((response.body()!!.total!! / 30) > 0) {
                        maxPageCountLiveData.value = (response.body()!!.total!! / 30) + 1
                    } else {
                        maxPageCountLiveData.value = response.body()!!.total!! / 30
                    }
                    viewDataLiveData.value = response.body()!!.data!!
                    for (i in 0 until viewDataLiveData.value!!.size) {
                        viewDataArray.value!!.add(viewDataLiveData.value!![i])
                    }
                    viewDataArray.value = viewDataArray.value
                }
            }

            override fun onFailure(call: Call<TravelData>, t: Throwable) {
                Log.d("Home", "error: ${t.message.toString()}")
                when (apiLang) {
                    "zh-tw" -> {
                        failConnectLiveData.value = "獲取資料失敗"
                    }
                    "en" -> {
                        failConnectLiveData.value = "Fail to get data"
                    }
                    "ja" -> {
                        failConnectLiveData.value = "データの取得に失敗しました"
                    }
                }
            }

        })
    }

    fun changeLangList(changeLang: String) {
        getTravelData(changeLang)
    }

    fun loadMore(apiLang: String) {
        pageCountLiveData.value = pageCountLiveData.value?.plus(1)
        if (pageCountLiveData.value!! <= maxPageCountLiveData.value!!) {
            getTravelData(apiLang)
        }
    }

    fun setCategoryId(categoryId: String) {
        categoryIdsLiveData.value = categoryId
    }

    fun observeMaxPageCount(): MutableLiveData<Int?> {
        return maxPageCountLiveData
    }

    fun observePageCountLiveData(): LiveData<Int> {
        if (pageCountLiveData.value.toString().isEmpty()) {
            pageCountLiveData.value = 1
        }
        return pageCountLiveData
    }

    fun observeViewDataArray(): MutableLiveData<ArrayList<TravelData.ViewData>> {
        return viewDataArray
    }

}