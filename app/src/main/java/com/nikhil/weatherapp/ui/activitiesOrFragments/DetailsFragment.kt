package com.nikhil.weatherapp.ui.activitiesOrFragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.databinding.FragmentDetailsBinding

class DetailsFragment() :
    Fragment(R.layout.fragment_details) {

    private lateinit var detailsFragment: FragmentDetailsBinding

    lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsFragment = FragmentDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)

        val detailsFragmentArgs = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        val weatherPOJO = detailsFragmentArgs!!.weather

        // Set title bar
        if (weatherPOJO != null) {
            (activity as AppCompatActivity).supportActionBar?.title = weatherPOJO.cityName
        }

        detailsFragment.cityAdapterItem = weatherPOJO
        detailsFragment.imageViewConditionIcon.setImageDrawable(
            resources.getDrawable(
                R.drawable.ic_weather_sunny,
                null
            )
        )

        detailsFragment.imageViewConditionIcon.setOnClickListener {
            navController.navigate(R.id.action_detailsFragment_to_homeFragment);
        }

    }

}