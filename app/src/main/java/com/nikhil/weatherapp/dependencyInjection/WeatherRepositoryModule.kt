package com.nikhil.weatherapp.dependencyInjection

import com.nikhil.weatherapp.networkApis.IWeatherApi
import com.nikhil.weatherapp.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object WeatherRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(iWeatherApi: IWeatherApi): WeatherRepository {
        return WeatherRepository(iWeatherApi)
    }

}