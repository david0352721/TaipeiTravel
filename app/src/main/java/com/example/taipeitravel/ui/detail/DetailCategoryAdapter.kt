package com.example.taipeitravel.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitravel.databinding.ItemDetailCategoryBinding
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.DetailCategoryClickListener

class DetailCategoryAdapter(private val detailCategoryClickListener: DetailCategoryClickListener)
    : RecyclerView.Adapter<DetailCategoryAdapter.ViewHolder>() {

    private var viewCategoryList = ArrayList<TravelData.ViewData.ViewCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setViewCategoryList(viewCategoryList: List<TravelData.ViewData.ViewCategory>) {
        this.viewCategoryList = viewCategoryList as ArrayList<TravelData.ViewData.ViewCategory>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemDetailCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailCategoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.detailCategoryId.text = viewCategoryList[position].id.toString()
        holder.binding.detailCategoryName.text = "${viewCategoryList[position].name},"
        holder.binding.detailCategoryName.setOnClickListener {
            detailCategoryClickListener.onDetailCategoryClickListener(viewCategoryList[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return viewCategoryList.size
    }

}