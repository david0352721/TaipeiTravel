package com.example.taipeitravel.ui.web

import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {

    var viewOfficialSite = MutableLiveData<String>()

    fun observeViewOfficialSite(): MutableLiveData<String> {
        return viewOfficialSite
    }

}