package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.ForecastResponse
import com.sunnyweather.android.logic.model.NowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v7/weather/3d?key=${SunnyWeatherApplication.TOKEN}")
    fun getDailyWeather(@Query("location") query: String): Call<ForecastResponse>

    @GET("v7/weather/now?key=${SunnyWeatherApplication.TOKEN}")
    fun getNowWeather(@Query("location") query:String):Call<NowResponse>
}