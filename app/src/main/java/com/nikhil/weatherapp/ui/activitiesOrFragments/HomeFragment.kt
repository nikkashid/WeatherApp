package com.nikhil.weatherapp.ui.activitiesOrFragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.constants.Constants
import com.nikhil.weatherapp.databinding.FragmentHomeBinding
import com.nikhil.weatherapp.entities.WeatherEntity
import com.nikhil.weatherapp.ui.adapters.WeatherListAdapter
import com.nikhil.weatherapp.viewModel.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home), WeatherListAdapter.IClickListener {

    private lateinit var mainActivity: FragmentHomeBinding

    //Hilt ViewModel Injection
    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var alertDialog: AlertDialog

    lateinit var navController: NavController

    var isButtonClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = FragmentHomeBinding.bind(view)

        navController = Navigation.findNavController(view)

        alertDialog = Constants.getProgressDialog(requireContext(), getString(R.string.please_wait))

        mainActivity.ivSearchCity.setOnClickListener {

            if (mainActivity.etSearchCity.length() > 0) {
                Constants.hideKeyboard(requireActivity())
                homeViewModel.getWeather(mainActivity.etSearchCity.text.toString())
                isButtonClicked = true
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.enter_city_name),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        homeViewModel.observerServerResponse().observe(viewLifecycleOwner,
            { response ->
                if (isButtonClicked) {
                    if (response is WeatherEntity) {
                        alertDialog.dismiss()

                        val weatherResponse: WeatherEntity = response

                        startFragment(weatherResponse)

                    } else if (response is String) {
                        if (response == Constants.NETWORK_HIT_INITIATED) {
                            alertDialog.show()
                        } else {
                            alertDialog.dismiss()
                            Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        alertDialog.dismiss()
                        Toast.makeText(
                            requireContext(),
                            Constants.ERROR_MSG,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })

        homeViewModel.observerDBData().observe(viewLifecycleOwner, { response ->
            setUpAdapter(response)
        })

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun startFragment(weatherResponse: WeatherEntity) {
        isButtonClicked = false
        var action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(weatherResponse)
        navController.navigate(action)
    }

    private fun setUpAdapter(listOfWeathers: List<WeatherEntity>) {
        if (listOfWeathers.isNotEmpty()) {
            val weatherListAdapter = WeatherListAdapter(this)
            mainActivity.rvSearchData.layoutManager = LinearLayoutManager(requireContext())
            mainActivity.rvSearchData.hasFixedSize()
            mainActivity.rvSearchData.adapter = weatherListAdapter
            weatherListAdapter.submitList(listOfWeathers)
        }
    }

    override fun onClickListener(weatherEntity: WeatherEntity) {
        startFragment(weatherEntity)
    }

}