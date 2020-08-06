package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.NowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NowService {
    @GET("v7/weather/now?key=${SunnyWeatherApplication.TOKEN}")
    fun searchNow(@Query("location") query:String):Call<NowResponse>
}