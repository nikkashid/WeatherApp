package com.nikhil.weatherapp.constants

import android.app.AlertDialog
import android.content.Context
import dmax.dialog.SpotsDialog

object Constants {

    const val SUCCESS_MSG = "Data Fetched"

    const val ERROR_MSG = "Something went wrong"

    const val NETWORK_HIT_INITIATED = "Network Hit Initiated"

    const val API_ID = ""

    fun getProgressDialog(context: Context, msg: String): AlertDialog {

        return SpotsDialog.Builder()
            .setContext(context)
            .setMessage(msg)
            .setCancelable(false)
            .build()
            .apply {
                //show()
            }
    }

}
