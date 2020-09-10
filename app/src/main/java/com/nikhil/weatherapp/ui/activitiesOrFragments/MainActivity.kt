package com.nikhil.weatherapp.ui.activitiesOrFragments

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.constants.Constants
import com.nikhil.weatherapp.databinding.ActivityMainBinding
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainActivity: ActivityMainBinding

    //Hilt ViewModel Injection
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        alertDialog = Constants.getProgressDialog(this, getString(R.string.please_wait))

        homeViewModel.getWeather()

        homeViewModel.observerServerResponse().observe(this,
            { response ->
                if (response is WeatherEntity) {
                    alertDialog.dismiss()
                    val weatherResponse: WeatherEntity = response

                    val detailsFragment = DetailsFragment(weatherResponse)
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragment_container, detailsFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                } else if (response is String) {
                    if (response == Constants.NETWORK_HIT_INITIATED) {
                        alertDialog.show()
                    } else {
                        alertDialog.dismiss()
                        Toast.makeText(this, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    alertDialog.dismiss()
                    Toast.makeText(this, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }
}