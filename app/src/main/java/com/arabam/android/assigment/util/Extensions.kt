package com.arabam.android.assigment.util

fun String.replacePhotoForList() : String{
    return this.replace("{0}", "580x435")
}

fun String.replacePhotoForDetail() : String{
    return this.replace("{0}", "1920x1080")
}