package com.example.prayertimes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://namr97002-boop.github.io/Al-Noor/")
        webView.addJavascriptInterface(this, "Android")
    }

    @android.webkit.JavascriptInterface
    fun schedulePrayer(name: String, label: String, timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AdhanReceiver::class.java).apply {
            putExtra("PRAYER_NAME", label)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            name.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }

    @android.webkit.JavascriptInterface
    fun cancelAllPrayers() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (prayer in listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")) {
            val intent = Intent(this, AdhanReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                prayer.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}
