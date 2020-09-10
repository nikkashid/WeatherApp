package com.nikhil.weatherapp.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.nikhil.weatherapp.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDataBase::class.java,
        "WEATHER_DB"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: AppDataBase) = db.weatherDao()

}