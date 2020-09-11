package com.nikhil.weatherapp.repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikhil.weatherapp.BuildConfig
import com.nikhil.weatherapp.constants.Constants
import com.nikhil.weatherapp.database.WeatherDetailsDao
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.networkApis.IWeatherApi
import com.nikhil.weatherapp.responses.cityResponse.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: IWeatherApi,
    private val weatherDetailDao: WeatherDetailsDao
) {
    private val TAG = "WeatherRepository"
    
    private var response: MutableLiveData<Any> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getCityWeatherFromNetwork(cityName: String) {

        setResponse(Constants.NETWORK_HIT_INITIATED)

        weatherApi.getWeather(
            cityName, BuildConfig.APPID, "metric"
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                override fun onSuccess(t: WeatherResponse) {
                    insertDataIntoDB(t)
                }

                override fun onError(e: Throwable) {
                    setResponse(Constants.ERROR_MSG)
                }
            })
    }

    private fun insertDataIntoDB(weatherPojo: WeatherResponse) {

        try {
            var weatherEntity = WeatherEntity()
            weatherEntity.cityName = weatherPojo.name
            weatherEntity.weatherDescription = weatherPojo.weather[0].description
            weatherEntity.temp = weatherPojo.main.temp.toString()
            weatherEntity.feels_like = weatherPojo.main.feels_like.toString()
            weatherEntity.tempMin = weatherPojo.main.temp_min.toString()
            weatherEntity.tempMax = weatherPojo.main.temp_max.toString()
            weatherEntity.humidity = weatherPojo.main.humidity.toString()
            weatherEntity.visibility = weatherPojo.visibility.toString()
            weatherEntity.wind_speed = weatherPojo.wind.speed.toString()
            weatherEntity.dataEnteredTime = "${System.currentTimeMillis()}"
            weatherDetailDao.insert(weatherEntity)
            setResponse(weatherEntity)
        } catch (e: Exception) {
            Log.e(TAG, "insertDataIntoDB: $e", )
        }

    }

    private fun setResponse(serverMsg: Any) {
        response.postValue(serverMsg)
    }

    fun observerServerResponse(): MutableLiveData<Any> {
        return response
    }

    fun observerDBData(): LiveData<List<WeatherEntity>> {
        return weatherDetailDao.getAllData()
    }
}