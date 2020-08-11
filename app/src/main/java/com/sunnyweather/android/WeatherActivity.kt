package com.sunnyweather.android

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import com.sunnyweather.android.ui.weather.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.now.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_weather)

        if (viewModel.location.isEmpty()) {
            viewModel.location = intent.getStringExtra("locationID") ?:""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?:""
        }

        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this,"无法成功获取天气信息",Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.location)
    }

    private fun showWeatherInfo(weather:Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.now
        val daily = weather.daily
        //填充now.xml
        val currentTempText = "${realtime.temp.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.icon).info
        val currentPM25Text = "空气指数 ${realtime.wind360}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.icon).bg)
        //填充forecast
        forecastLayout.removeAllViews()
        for (day in daily) {
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
            val skycon = day.iconDay
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            dateInfo.text = day.fxDate
            val sky = getSky(skycon)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${day.tempMin} ~ ${day.tempMax} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        weather_layout.visibility = View.VISIBLE
    }
}