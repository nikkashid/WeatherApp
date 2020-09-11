package com.nikhil.weatherapp.ui.activitiesOrFragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.databinding.FragmentDetailsBinding
import com.nikhil.weatherapp.entities.WeatherEntity

class DetailsFragment(private val weatherPOJO: WeatherEntity) :
    Fragment(R.layout.fragment_details) {

    var detailsFragment: FragmentDetailsBinding? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsFragment = FragmentDetailsBinding.bind(view)

        // Set title bar
        (activity as AppCompatActivity).supportActionBar?.title = weatherPOJO.cityName

        detailsFragment!!.textViewDescription.text = weatherPOJO.weatherDescription.toUpperCase()
        detailsFragment!!.textViewFeelsLikeTemperature.text =
            "Feels like ${weatherPOJO.feels_like}°C"
        detailsFragment!!.textViewTemperature.text = "${weatherPOJO.temp}°C"
        detailsFragment!!.textViewMaxTemp.text = "Max Temp : ${weatherPOJO.tempMax}°C"
        detailsFragment!!.textViewMinTemp.text = "Min Temp : ${weatherPOJO.tempMin}°C"
        detailsFragment!!.textViewHumidity.text = "Humidity : ${weatherPOJO.humidity}"
        detailsFragment!!.textViewVisibility.text = "Visibility: ${weatherPOJO.visibility} km"
        detailsFragment!!.textViewWind.text = "Wind: ${weatherPOJO.wind_speed} m/s "
        detailsFragment!!.imageViewConditionIcon.setImageDrawable(
            resources.getDrawable(
                R.drawable.ic_weather_sunny,
                null
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsFragment = null
    }

}