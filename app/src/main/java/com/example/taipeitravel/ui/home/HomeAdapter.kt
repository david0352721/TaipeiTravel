package com.example.taipeitravel.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeitravel.R
import com.example.taipeitravel.databinding.ItemHomeBinding
import com.example.taipeitravel.model.TravelData
import com.example.taipeitravel.repository.HomeListClickListener
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(private val homeListClickListener: HomeListClickListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var viewDataList = ArrayList<TravelData.ViewData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setViewDataList(viewDataList: List<TravelData.ViewData>) {
        this.viewDataList = viewDataList as ArrayList<TravelData.ViewData>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemHomeName.text = viewDataList[position].name
        holder.binding.itemHomeDescription.text = viewDataList[position].introduction
        if (viewDataList[position].images!!.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(viewDataList[position].images!![0].src)
                .into(holder.binding.itemHomeImage)
        } else {
            holder.binding.itemHomeImage.setImageResource(R.drawable.ic_launcher_background)
        }
        holder.binding.itemHomeGoDetail.setOnClickListener {
            homeListClickListener.onHomeListClickListener(viewDataList[position])
        }
    }

    override fun getItemCount(): Int {
        return viewDataList.size
    }


}