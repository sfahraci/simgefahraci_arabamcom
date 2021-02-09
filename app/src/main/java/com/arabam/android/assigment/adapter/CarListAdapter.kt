package com.arabam.android.assigment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arabam.android.assigment.adapter.viewholders.CarListVH
import com.arabam.android.assigment.databinding.RowCarBinding
import com.arabam.android.assigment.model.Car

//https://stackoverflow.com/a/24471410/4862911
class CarListAdapter(private val mOnClickListener: View.OnClickListener?) : RecyclerView.Adapter<CarListVH>() {


    var carList : ArrayList<Car> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListVH {
        val itemBinding = RowCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //https://stackoverflow.com/a/24471410/4862911
        itemBinding.root.setOnClickListener(mOnClickListener);
        return CarListVH(itemBinding)
    }

    override fun onBindViewHolder(holder: CarListVH, position: Int) {
        holder.bind(this.carList[position])
    }

    override fun getItemCount(): Int {
        return this.carList.size
    }

    fun setAdapterItems(carList: List<Car>){
        this.carList.clear()
        this.carList.addAll(carList)


        notifyDataSetChanged()
    }
}