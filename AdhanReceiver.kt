package com.muslimapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class AdhanReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val serviceIntent = Intent(context, AdhanService::class.java)
        if (Build.VERSION.SDK_INT >= 26)
            context.startForegroundService(serviceIntent)
        else
            context.startService(serviceIntent)
    }
}
