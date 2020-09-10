package com.nikhil.weatherapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "city_name") var cityName: String,
    @ColumnInfo(name = "weather_description") var weatherDescription: String,
    @ColumnInfo(name = "temp_min") var tempMin: String,
    @ColumnInfo(name = "temp_max") var tempMax: String,
    @ColumnInfo(name = "humidity") var humidity: String,
    @ColumnInfo(name = "data_entered_time") var dataEnteredTime: String
)