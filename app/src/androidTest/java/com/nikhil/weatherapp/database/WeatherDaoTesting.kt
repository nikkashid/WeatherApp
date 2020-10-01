package com.nikhil.weatherapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTesting {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDataBase

    private lateinit var weatherDetailsDao: WeatherDetailsDao

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDataBase::class.java
        ).allowMainThreadQueries().build()

        weatherDetailsDao = appDatabase.weatherDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertWeatherData() = runBlockingTest {

        val weatherEntity = WeatherEntity()
        weatherEntity.cityName = "Kolhapur"
        weatherEntity.weatherDescription = "Cloudy"
        weatherEntity.temp = "25.34"
        weatherEntity.feels_like = "25.34"
        weatherEntity.tempMin = "25.34"
        weatherEntity.tempMax = "25.34"
        weatherEntity.humidity = "90"
        weatherEntity.visibility = "10000"
        weatherEntity.wind_speed = "3.14"
        weatherEntity.dataEnteredTime = "${System.currentTimeMillis()}"
        weatherDetailsDao.insert(weatherEntity)

        val completeWeatherInfo = weatherDetailsDao.getAllData().getOrAwaitValue()

        assertThat(completeWeatherInfo).contains(weatherEntity)

    }

    @Test
    fun deleteWeatherDao() = runBlockingTest {
        val weatherEntity = WeatherEntity()
        weatherEntity.cityName = "Kolhapur"
        weatherEntity.weatherDescription = "Cloudy"
        weatherEntity.temp = "25.34"
        weatherEntity.feels_like = "25.34"
        weatherEntity.tempMin = "25.34"
        weatherEntity.tempMax = "25.34"
        weatherEntity.humidity = "90"
        weatherEntity.visibility = "10000"
        weatherEntity.wind_speed = "3.14"
        weatherEntity.dataEnteredTime = "${System.currentTimeMillis()}"
        weatherDetailsDao.insert(weatherEntity)
        weatherDetailsDao.delete(weatherEntity)

        val completeWeatherInfo = weatherDetailsDao.getAllData().getOrAwaitValue()

        assertThat(completeWeatherInfo).doesNotContain(weatherEntity)
    }

    @Test
    fun getDataCount() = runBlockingTest {

        for (i in 1..2) {
            val weatherEntity = WeatherEntity(
                i,
                "Kolhapur",
                "Cloudy",
                "25.34",
                "25.34",
                "25.34",
                "25.34",
                "90",
                "10000",
                "3.14",
                "${System.currentTimeMillis()}"
            )

            weatherDetailsDao.insert(weatherEntity)
        }

        val completeWeatherInfo = weatherDetailsDao.getAllData().getOrAwaitValue()
        val dbCount = weatherDetailsDao.getDataCount()

        assertThat(completeWeatherInfo.size).isEqualTo(dbCount)

    }

}