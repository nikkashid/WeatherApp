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

@SuppressLint("CheckResult")
class WeatherRepository @Inject constructor(
    private val weatherApi: IWeatherApi,
    private val weatherDetailDao: WeatherDetailsDao
) {
    private val TAG = "WeatherRepository"

    private var response: MutableLiveData<Any> = MutableLiveData()

    fun getCityWeatherInfo(cityName: String) {
        checkDataInDB(cityName.toUpperCase())
    }

    private fun checkDataInDB(cityName: String) {

        weatherDetailDao.ifCityExists(cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<WeatherEntity>>() {
                override fun onSuccess(liveData: List<WeatherEntity>) {
                    if (liveData.isNotEmpty()) {
                        checkExpiryTime(liveData[0], cityName)
                    } else {
                        getCityWeatherFromNetwork(cityName)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                    setResponse(Constants.ERROR_MSG)
                }
            })
    }

    private fun checkExpiryTime(requestedData: WeatherEntity, cityName: String) {

        weatherDetailDao.getDataEnteredTime(cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<String>() {
                override fun onSuccess(enteredTime: String) {
                    val enteredTime = enteredTime.toLong()
                    val currentTime = System.currentTimeMillis()

                    val timeDifference = currentTime - enteredTime

                    Log.d(TAG, "Time difference :$timeDifference")

                    if (timeDifference > 1000 * 60 * 60 * 24) {
                        deleteExistingData(requestedData, cityName)
                    } else {
                        setResponse(requestedData)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                    setResponse(Constants.ERROR_MSG)
                }
            })
    }

    private fun deleteExistingData(requestedData: WeatherEntity, cityName: String) {

        weatherDetailDao.delete(requestedData).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Int>() {
                override fun onSuccess(init: Int) {
                    getCityWeatherFromNetwork(cityName)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                    setResponse(Constants.ERROR_MSG)
                }
            })

    }

    @SuppressLint("CheckResult")
    fun getCityWeatherFromNetwork(cityName: String) {

        setResponse(Constants.NETWORK_HIT_INITIATED)

        weatherApi.getWeather(
            cityName, BuildConfig.APPID, "metric"
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                override fun onSuccess(t: WeatherResponse) {
                    insertDataIntoDB(t, cityName)
                }

                override fun onError(e: Throwable) {
                    if (e.message.equals(Constants.CITY_NOT_FOUND_RESPONSE)) {
                        setResponse(Constants.CITY_NOT_FOUND)
                    } else {
                        setResponse(Constants.ERROR_MSG)
                    }
                }
            })
    }

    private fun insertDataIntoDB(weatherPojo: WeatherResponse, cityName: String) {

        try {
            var weatherEntity = WeatherEntity()
            /*Added because name from server sometimes had special char in it
            eg. Kolhapur used to come as Kolhāpur */
            weatherEntity.cityName = cityName.toUpperCase()
            weatherEntity.weatherDescription = weatherPojo.weather[0].description.toUpperCase()
            weatherEntity.temp = weatherPojo.main.temp.toString()
            weatherEntity.feels_like = weatherPojo.main.feels_like.toString()
            weatherEntity.tempMin = weatherPojo.main.temp_min.toString()
            weatherEntity.tempMax = weatherPojo.main.temp_max.toString()
            weatherEntity.humidity = weatherPojo.main.humidity.toString()
            weatherEntity.visibility = weatherPojo.visibility.toString()
            weatherEntity.wind_speed = weatherPojo.wind.speed.toString()
            weatherEntity.dataEnteredTime = "${System.currentTimeMillis()}"

            weatherDetailDao.insert(weatherEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Long?>() {
                    override fun onSuccess(aLong: Long) {
                        setResponse(weatherEntity)
                    }

                    override fun onError(e: Throwable) {
                        setResponse(Constants.ERROR_MSG)
                    }
                })

        } catch (e: Exception) {
            Log.e(TAG, "insertDataIntoDB: $e")
        }

    }

    private fun setResponse(serverMsg: Any) {
        response.postValue(serverMsg)
        getDBCountFromDB()
    }

    fun observerServerResponse(): MutableLiveData<Any> {
        return response
    }

    fun observerDBData(): LiveData<List<WeatherEntity>> {
        return weatherDetailDao.getAllData()
    }

    private fun getDBCountFromDB() {

        weatherDetailDao.getDataCount().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Long?>() {
                override fun onSuccess(aLong: Long) {
                    Log.d(
                        TAG, "Data count from DB :$aLong"
                    )
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                }
            })
    }

}