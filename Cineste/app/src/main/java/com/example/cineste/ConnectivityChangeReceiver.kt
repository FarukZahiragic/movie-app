package com.example.cineste

import android.content.BroadcastReceiver
import android.widget.Toast
import android.content.Context
import android.content.Intent

class ConnectivityChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isWiFiOn = intent?.getBooleanExtra("state", false) ?: return

        if(isWiFiOn)
            Toast.makeText(context, "No Internet connection", Toast.LENGTH_LONG).show()
    }
}
