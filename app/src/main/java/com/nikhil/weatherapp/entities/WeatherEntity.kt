package com.nikhil.weatherapp.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "city_name")
    var cityName: String = "",

    @ColumnInfo(name = "weather_description")
    var weatherDescription: String = "",

    @ColumnInfo(name = "temp")
    var temp: String = "",

    @ColumnInfo(name = "feels_like")
    var feels_like: String = "",

    @ColumnInfo(name = "temp_min")
    var tempMin: String = "",

    @ColumnInfo(name = "temp_max")
    var tempMax: String = "",

    @ColumnInfo(name = "humidity")
    var humidity: String = "",

    @ColumnInfo(name = "visibility")
    var visibility: String = "",

    @ColumnInfo(name = "wind_speed")
    var wind_speed: String = "",

    @ColumnInfo(name = "data_entered_time")
    var dataEnteredTime: String = ""
) : Parcelable {


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        cityName = parcel.readString().toString()
        weatherDescription = parcel.readString().toString()
        temp = parcel.readString().toString()
        feels_like = parcel.readString().toString()
        tempMin = parcel.readString().toString()
        tempMax = parcel.readString().toString()
        humidity = parcel.readString().toString()
        visibility = parcel.readString().toString()
        wind_speed = parcel.readString().toString()
        dataEnteredTime = parcel.readString().toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherEntity

        if (id != other.id) return false
        if (cityName != other.cityName) return false
        if (weatherDescription != other.weatherDescription) return false
        if (temp != other.temp) return false
        if (feels_like != other.feels_like) return false
        if (tempMin != other.tempMin) return false
        if (tempMax != other.tempMax) return false
        if (humidity != other.humidity) return false
        if (visibility != other.visibility) return false
        if (wind_speed != other.wind_speed) return false
        if (dataEnteredTime != other.dataEnteredTime) return false

        return true
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        //Written because of forcefull implementing
    }

    companion object CREATOR : Parcelable.Creator<WeatherEntity> {
        override fun createFromParcel(parcel: Parcel): WeatherEntity {
            return WeatherEntity(parcel)
        }

        override fun newArray(size: Int): Array<WeatherEntity?> {
            return arrayOfNulls(size)
        }
    }

}