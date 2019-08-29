package com.example.gostick_test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar

class CheckConnectionBroadcast(val view: View): BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (ConnectivityManager.EXTRA_NO_CONNECTIVITY == p1!!.action){
            Snackbar.make(view, "network changed", Snackbar.LENGTH_INDEFINITE).show()
        }
    }
}