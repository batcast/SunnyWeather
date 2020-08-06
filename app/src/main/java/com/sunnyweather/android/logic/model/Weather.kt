package com.sunnyweather.android.logic.model

data class Weather(val now:NowResponse.Now,val daily:List<ForecastResponse.Daily>)