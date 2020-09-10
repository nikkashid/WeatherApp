package com.nikhil.weatherapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
class WeatherEntity() {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "city_name")
    var cityName: String = ""

    @ColumnInfo(name = "weather_description")
    var weatherDescription: String = ""

    @ColumnInfo(name = "temp")
    var temp: String = ""

    @ColumnInfo(name = "feels_like")
    var feels_like: String = ""

    @ColumnInfo(name = "temp_min")
    var tempMin: String = ""

    @ColumnInfo(name = "temp_max")
    var tempMax: String = ""

    @ColumnInfo(name = "humidity")
    var humidity: String = ""

    @ColumnInfo(name = "visibility")
    var visibility: String = ""

    @ColumnInfo(name = "wind_speed")
    var wind_speed: String = ""

    @ColumnInfo(name = "data_entered_time")
    var dataEnteredTime: String = ""
}