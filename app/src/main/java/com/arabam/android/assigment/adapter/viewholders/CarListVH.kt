package com.arabam.android.assigment.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.arabam.android.assigment.R
import com.arabam.android.assigment.databinding.RowCarBinding
import com.arabam.android.assigment.model.Car
import com.bumptech.glide.Glide

class CarListVH(private val binding: RowCarBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(carmodel : Car){
        binding.titleTV.text = carmodel.title
        binding.dateTV.text = carmodel.date
        binding.modelNameTV.text = carmodel.modelName
        binding.priceFormattedTV.text = carmodel.priceFormatted

        Glide.with(binding.root.context).load(carmodel.photo).placeholder(R.drawable.not_found).fitCenter().into(binding.carIV)

    }
}