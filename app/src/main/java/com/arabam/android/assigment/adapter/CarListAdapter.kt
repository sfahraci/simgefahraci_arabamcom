package com.arabam.android.assigment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arabam.android.assigment.R
import com.arabam.android.assigment.adapter.viewholders.CarListVH
import com.arabam.android.assigment.databinding.RowCarBinding
import com.arabam.android.assigment.model.Car

class CarListAdapter(private val carList : List<Car>) : RecyclerView.Adapter<CarListVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        val itemBinding = RowCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarListVH(itemBinding)
    }

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        holder.bind(this.carList[position])
    }

    override fun getItemCount(): Int {
        return this.carList.size
    }
}