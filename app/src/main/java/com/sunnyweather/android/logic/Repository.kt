package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.NowResponse
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(query:String) = liveData(Dispatchers.IO){
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="200"){
                val locations = placeResponse.location
                Result.success(locations)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e : Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun searchNow(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val nowResponse = SunnyWeatherNetwork.searchNow(query)
            if (nowResponse.code == "200"){
                val now = nowResponse.now
                Result.success(now)
            }else{
                Result.failure(RuntimeException("response status is ${nowResponse.code}"))
            }
        }catch (e:Exception){
            Result.failure<NowResponse.Now>(e)
        }
        emit(result)
    }
}