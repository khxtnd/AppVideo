package com.client.app

import android.app.Application

class DataBaseApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        share = this
    }

    companion object {

        lateinit var share: Application
    }
}