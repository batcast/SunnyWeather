package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    @SuppressLint("StaticFieldLeak")
    companion object {
        const val TOKEN = "75e3c3429c184a83b134e1c23db5ab6f"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}