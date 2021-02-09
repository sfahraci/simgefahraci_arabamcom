package com.arabam.android.assigment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.arabam.android.assigment.adapter.CarListAdapter
import com.arabam.android.assigment.databinding.ActivityMainBinding
import com.arabam.android.assigment.databinding.ContentMainBinding
import com.arabam.android.assigment.model.Car
import com.arabam.android.assigment.service.ServiceConnector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var contentBinding : ContentMainBinding
    private var adapter : CarListAdapter = CarListAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        contentBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

    }

    private fun prepareView(){

    }

    private fun getData(){
        ServiceConnector.canliSkorAPI?.getProductList()?.enqueue(object : Callback<List<Car>>{
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {

                val gridLayoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
                binding.recylerView.layoutManager = gridLayoutManager

                adapter = CarListAdapter(response.body()!!)
                binding.recylerView.adapter = adapter
                Toast.makeText(this@MainActivity, "success", Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
            }

        })
    }

}