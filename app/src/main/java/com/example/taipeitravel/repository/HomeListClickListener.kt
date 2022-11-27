package com.example.taipeitravel.repository

import android.view.View
import com.example.taipeitravel.model.TravelData

interface HomeListClickListener {

    fun onHomeListClickListener(viewData: TravelData.ViewData)

}