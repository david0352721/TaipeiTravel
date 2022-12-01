package com.example.taipeitravel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class TravelData(
    val total: Int? = 0,
    val data: List<ViewData>?
) {
    @Parcelize
    data class ViewData(
        val id: Int? = 0,
        val name: String? = "",
        val name_zh: String? = "",
        val open_status: Int? = 1,
        val introduction: String? = "",
        val open_time: String? = "",
        val zipcode: String? = "",
        val distric: String? = "",
        val address: String? = "",
        val tel: String? = "",
        val email: String? = "",
        val months: String? = "",
        val nlat: Double? = 0.0,
        val elong: Double? = 0.0,
        val official_site: String? = "",
        val facebook: String? = "",
        val ticket: String? = "",
        val remind: String? = "",
        val staytime: String? = "",
        val modified: String? = "",
        val url: String? = "",
        val category: List<ViewCategory>?,
        val target: List<ViewTarget>?,
        val service: List<ViewService>?,
        val images: List<ViewImage>?,
        val links: List<ViewLink>?
    )
        : Parcelable
    {
        @Parcelize
        data class ViewCategory(
            val id: Int? = 0,
            val name: String? = ""
        ):Parcelable
        @Parcelize
        data class ViewTarget(
            val id: Int? = 0,
            val name: String? = ""
        ): Parcelable
        @Parcelize
        data class ViewService(
            val id: Int? = 0,
            val name: String? = ""
        ): Parcelable
        @Parcelize
        data class ViewImage(
            val src: String? = "",
            val subject: String? = "",
            val ext: String? = ""
        ): Parcelable
        @Parcelize
        data class ViewLink(
            val src: String? = "",
            val subject: String? = ""
        ): Parcelable
    }
}
