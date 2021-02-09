package com.arabam.android.assigment

import android.app.Application
import com.arabam.android.assigment.service.ServiceConnector

class ArabamcomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceConnector.init()
    }
}