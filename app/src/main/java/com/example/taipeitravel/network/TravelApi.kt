package com.example.taipeitravel.network

import com.example.taipeitravel.model.TravelData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface TravelApi {

    @Headers("Accept: application/json")
    @GET("zh-tw/Attractions/All?Page=1")
    fun getAllViews(): Call<TravelData>

}