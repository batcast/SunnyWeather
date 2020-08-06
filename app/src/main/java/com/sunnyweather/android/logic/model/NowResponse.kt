package com.sunnyweather.android.logic.model

data class NowResponse(
    val code: String,
    val updateTime: String,
    val fxLink: String,
    val now: Now,
    val refer: Refer
) {
    data class Now(
        val obsTime: String,
        val temp: String,
        val feelsLike: String,
        val icon: String,
        val text: String,
        val wind360: String,
        val windDir: String,
        val windScale: String,
        val windSpeed: String,
        val humidity: String,
        val precip: String,
        val pressure: String,
        val vis: String,
        val cloud: String,
        val dew: String
    )

    data class Refer(val sources: List<String>, val license: List<String>)
}