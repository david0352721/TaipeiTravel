package com.example.taipeitravel.network

import com.example.taipeitravel.model.TravelData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {

    @Headers("Accept: application/json")
    @GET("{lang}/Attractions/All")
    fun getAllViews(@Path("lang") lang: String, @Query("page") page: Int): Call<TravelData>

}