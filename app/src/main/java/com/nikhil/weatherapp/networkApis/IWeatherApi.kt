package com.nikhil.weatherapp.networkApis

import com.nikhil.weatherapp.BuildConfig
import com.nikhil.weatherapp.responses.cityResponse.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {

    @GET(BuildConfig.WEATHER)
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") appID: String
    ): Single<WeatherResponse>

}