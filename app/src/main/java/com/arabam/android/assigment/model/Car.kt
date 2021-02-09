package com.arabam.android.assigment.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Car(
    val id : Int,
    val title : String,
    val modelName : String,
    val priceFormatted : String,
    val dateFormatted : String,
    val photo : String,
    val photos : List<String>,
    val text : String
)