package com.nikhil.weatherapp.constants

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import dmax.dialog.SpotsDialog

object Constants {

    const val CITY_NOT_FOUND_RESPONSE = "HTTP 404 Not Found"

    const val ERROR_MSG = "Something went wrong"

    const val CITY_NOT_FOUND = "City Not Found"

    const val NETWORK_HIT_INITIATED = "Network Hit Initiated"

    const val WEATHER_UNIT = "metric"

    fun getProgressDialog(context: Context, msg: String): AlertDialog {

        return SpotsDialog.Builder()
            .setContext(context)
            .setMessage(msg)
            .setCancelable(false)
            .build()
            .apply {

            }
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}
