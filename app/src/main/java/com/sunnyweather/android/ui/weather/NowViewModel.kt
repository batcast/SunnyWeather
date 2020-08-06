package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.NowResponse
import com.sunnyweather.android.logic.model.NowResponse.Now

class NowViewModel :ViewModel() {
    private val searchLiveData = MutableLiveData<String>()



    val nowLiveData = Transformations.switchMap(searchLiveData) { query->
        Repository.searchNow(query)
    }
    fun searchNow(query:String) {
        searchLiveData.value = query
    }
}