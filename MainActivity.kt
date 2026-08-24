package com.muslimapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val web = findViewById<WebView>(R.id.webView)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = true
        web.settings.mediaPlaybackRequiresUserGesture = false

        web.addJavascriptInterface(AndroidBridge(this), "Android")
        web.webViewClient = WebViewClient()

        web.loadUrl("file:///android_asset/index_final_fixed.html")

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }

    class AndroidBridge(private val context: Context) {
        @JavascriptInterface
        fun testAdhan() {
            val intent = Intent(context, AdhanService::class.java)
            if (Build.VERSION.SDK_INT >= 26)
                context.startForegroundService(intent)
            else
                context.startService(intent)
        }

        @JavascriptInterface
        fun scheduleAdhan(timeMillis: Long) {
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AdhanReceiver::class.java)
            val pi = PendingIntent.getBroadcast(
                context, timeMillis.hashCode(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (Build.VERSION.SDK_INT >= 23)
                alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeMillis, pi)
            else
                alarm.setExact(AlarmManager.RTC_WAKEUP, timeMillis, pi)
        }
    }
}
