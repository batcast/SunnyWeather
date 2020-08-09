package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "200") {
                val locations = placeResponse.location
                Result.success(locations)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
    }

    fun refreshWeather(location: String) = fire(Dispatchers.IO) {
            coroutineScope {
                val deferredNow = async {
                    SunnyWeatherNetwork.getNowWeather(location)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(location)
                }
                val nowResponse = deferredNow.await()
                val dailyResponse = deferredDaily.await()
                if (nowResponse.code == "200" && dailyResponse.code == "200") {
                    val weather = Weather(nowResponse.now, dailyResponse.daily)
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "now response code is ${nowResponse.code}" +
                                    "daily response code is ${dailyResponse.code}"
                        )
                    )
                }
            }
        }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}