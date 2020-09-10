package com.nikhil.weatherapp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.repositories.WeatherRepository

class HomeViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    fun getWeather(): Any? {
        return weatherRepository.getCityWeatherFromNetwork("Kolhapur")
    }

    fun observerDBData(): LiveData<List<WeatherEntity>> {
        return weatherRepository.observerDBData()
    }
}