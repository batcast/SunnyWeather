package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.PlaceResponse

class WeatherViewModel :ViewModel() {
    private val locationLiveData = MutableLiveData<String>()

    var location = ""

    var placeName = ""



    val weatherLiveData = Transformations.switchMap(locationLiveData) { location->
        Repository.refreshWeather(location)
    }
    fun refreshWeather(location:String) {
        locationLiveData.value = location
    }
}