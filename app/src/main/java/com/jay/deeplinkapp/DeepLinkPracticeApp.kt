package com.jay.deeplinkapp

import android.app.Application

class DeepLinkPracticeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: DeepLinkPracticeApp
            private set
    }
}