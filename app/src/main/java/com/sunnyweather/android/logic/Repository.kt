package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.NowResponse
import com.sunnyweather.android.logic.model.PlaceResponse
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "200") {
                val locations = placeResponse.location
                Result.success(locations)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<PlaceResponse.Place>>(e)
        }
        emit(result)
    }

    fun refreshWeather(location: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredNow = async {
                    SunnyWeatherNetwork.getNowWeather(location)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(location)
                }
                val nowResponse = deferredNow.await()
                val dailyResponse = deferredDaily.await()
                if (nowResponse.code == "200" && dailyResponse.code == "200"){
                    val weather = Weather(nowResponse.now,dailyResponse.daily)
                    Result.success(weather)
                }else{
                    Result.failure(RuntimeException("now response code is ${nowResponse.code}" +
                            "daily response code is ${dailyResponse.code}"))
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
}