package com.nikhil.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikhil.weatherapp.entities.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDetailsDao

}