package com.sunnyweather.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunnyweather.android.ui.weather.NowViewModel
import kotlinx.android.synthetic.main.now.*

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val viewModel by lazy { ViewModelProviders.of(this).get(NowViewModel::class.java) }
        viewModel.nowLiveData.observe(this, Observer { result ->
            val now = result.getOrNull()
            if (now != null) {
                degree_text.text = now.temp
                weather_info_text.text = now.text
            } else {
                Toast.makeText(this,"未能查询到天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}