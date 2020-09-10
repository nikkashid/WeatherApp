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

class DetailsFragment(private val weatherPojo: WeatherEntity) :
    Fragment(R.layout.fragment_details) {

    var detailsFragment: FragmentDetailsBinding? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsFragment = FragmentDetailsBinding.bind(view)

        // Set title bar
        (activity as AppCompatActivity).supportActionBar?.title = weatherPojo.cityName

        detailsFragment!!.textViewDescription.text = weatherPojo.weatherDescription.toUpperCase()
        detailsFragment!!.textViewFeelsLikeTemperature.text =
            "Feels like ${weatherPojo.feels_like}°C"
        detailsFragment!!.textViewTemperature.text = "${weatherPojo.temp}°C"
        detailsFragment!!.textViewVisibility.text = "Visibility: ${weatherPojo.visibility} km"
        detailsFragment!!.textViewWind.text = "Wind: ${weatherPojo.wind_speed} m/s "
        detailsFragment!!.imageViewConditionIcon.setImageDrawable(
            resources.getDrawable(
                R.drawable.ic_weather_sunny,
                null
            )
        )
    }

}