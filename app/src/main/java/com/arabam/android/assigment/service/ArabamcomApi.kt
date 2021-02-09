package com.arabam.android.assigment.service

import com.arabam.android.assigment.model.Car
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


 interface ArabamcomApi {


    @GET("listing?")
    fun getCarList(@QueryMap params : Map<String, Int>): Call<List<Car>>?

     @GET("detail?")
     fun getCarDetail(@QueryMap params : Map<String, Int>): Call<Car>?

}