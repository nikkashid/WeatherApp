package com.nikhil.weatherapp.ui.activitiesOrFragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.constants.Constants
import com.nikhil.weatherapp.databinding.ActivityMainBinding
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.ui.adapters.WeatherListAdapter
import com.nikhil.weatherapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WeatherListAdapter.IClickListener {

    private lateinit var mainActivity: ActivityMainBinding

    //Hilt ViewModel Injection
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var alertDialog: AlertDialog

    var firstInstance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)

        alertDialog = Constants.getProgressDialog(this, getString(R.string.please_wait))

        mainActivity.ivSearchCity.setOnClickListener {
            if (mainActivity.etSearchCity.length() > 0) {
                firstInstance++
                homeViewModel.getWeather(mainActivity.etSearchCity.text.toString())
            } else {
                Toast.makeText(this, "Please enter city name..", Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.observerServerResponse().observe(this,
            { response ->
                if (firstInstance > 0) {
                    if (response is WeatherEntity) {
                        alertDialog.dismiss()
                        Constants.hideKeyboard(this)

                        val weatherResponse: WeatherEntity = response

                        startFragment(weatherResponse)

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
                }
            })

        homeViewModel.observerDBData().observe(this, { resposne ->
            val listOfWeathers = resposne
            setUpAdapter(listOfWeathers)
        })
    }

    private fun startFragment(weatherResponse: WeatherEntity) {

        mainActivity.fragmentContainer.visibility = View.VISIBLE
        mainActivity.llSearchLayout.visibility = View.GONE
        mainActivity.rvSearchData.visibility = View.GONE

        val detailsFragment = DetailsFragment(weatherResponse)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, detailsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setUpAdapter(listOfWeathers: List<WeatherEntity>) {
        if (listOfWeathers.isNotEmpty()) {
            val weatherListAdapter = WeatherListAdapter(this)
            mainActivity.rvSearchData.layoutManager = LinearLayoutManager(this)
            mainActivity.rvSearchData.hasFixedSize()
            mainActivity.rvSearchData.adapter = weatherListAdapter
            weatherListAdapter.submitList(listOfWeathers)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val count: Int = supportFragmentManager.backStackEntryCount

        if (count != 0) {
            supportFragmentManager.popBackStackImmediate()
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun onBackPressed() {

        val count: Int = supportFragmentManager.backStackEntryCount

        supportActionBar?.setTitle(R.string.app_name)

        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStackImmediate()
            supportFragmentManager.popBackStackImmediate()
            mainActivity.llSearchLayout.visibility = View.VISIBLE
            mainActivity.rvSearchData.visibility = View.VISIBLE
        }
    }

    override fun onClickListener(weatherEntity: WeatherEntity) {
        startFragment(weatherEntity)
    }
}