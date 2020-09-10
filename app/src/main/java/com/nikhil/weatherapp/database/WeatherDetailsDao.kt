package com.nikhil.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nikhil.weatherapp.entities.WeatherEntity
import io.reactivex.Single

@Dao
interface WeatherDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherDetails: WeatherEntity): Single<Long>

    @Query("select * from weather_table")
    fun getAllData(): LiveData<List<WeatherEntity>>

    @Delete
    fun delete(weatherDetails: WeatherEntity): Single<Int>

    @Query("select data_entered_time from weather_table where city_name like :cityName")
    fun getDataEnteredTime(cityName: String): Single<String>

}