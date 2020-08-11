package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.PlaceResponse

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<PlaceResponse.Place>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""


    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lon, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value =
            PlaceResponse.Place("", "", lat, lng, "", "", "", "", "", "", "", "", "")
    }
}