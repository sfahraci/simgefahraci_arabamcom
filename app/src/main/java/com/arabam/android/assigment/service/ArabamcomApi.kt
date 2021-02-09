package com.arabam.android.assigment.service

import com.arabam.android.assigment.model.Car
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


 interface ArabamcomApi {


    @GET("listing?sort=1&sortDirection=0&take=10")
    fun getProductList(): Call<List<Car>>?

}