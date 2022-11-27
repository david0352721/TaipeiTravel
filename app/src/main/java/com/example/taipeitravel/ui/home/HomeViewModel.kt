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

    fun getTravelData() {
        TravelInstance.api.getAllViews().enqueue(object : Callback<TravelData>{
            override fun onResponse(call: Call<TravelData>, response: Response<TravelData>) {
                if (response.body() != null) {
                    viewDataLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<TravelData>, t: Throwable) {
                Log.d("Home", "error: ${t.message.toString()}")
            }

        })
    }

    fun observeViewDataLiveData(): LiveData<List<TravelData.ViewData>> {
        return viewDataLiveData
    }

}