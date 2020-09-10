package com.nikhil.weatherapp.responses.cityResponse

data class Main(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val feels_like: Double,
    val sea_level: Int,
    val grnd_level: Int
)