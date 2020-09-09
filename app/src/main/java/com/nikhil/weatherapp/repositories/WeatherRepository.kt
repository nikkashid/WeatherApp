package com.nikhil.weatherapp.repositories

import com.nikhil.weatherapp.BuildConfig
import com.nikhil.weatherapp.constants.Constants
import com.nikhil.weatherapp.networkApis.IWeatherApi
import com.nikhil.weatherapp.responses.cityResponse.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: IWeatherApi) {

    fun getCityWeatherFromNetwork(cityName: String) {

        weatherApi.getWeather(
            cityName, BuildConfig.APPID
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                override fun onSuccess(t: WeatherResponse) {
                    setResponse(Constants.SUCCESS_MSG)
                }

                override fun onError(e: Throwable) {
                    setResponse(Constants.ERROR_MSG)
                }
            })
    }

    private fun setResponse(serverMsg: String) {

    }
}