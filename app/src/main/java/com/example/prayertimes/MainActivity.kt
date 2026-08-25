package com.example.prayertimes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // إعداد WebView
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }

        webView.webViewClient = WebViewClient()

        // ربط Android مع JavaScript
        webView.addJavascriptInterface(this, "Android")

        // تحميل التطبيق
        webView.loadUrl("https://namr97002-boop.github.io/Al-Noor/")
    }

    @JavascriptInterface
    fun schedulePrayer(
        name: String,
        label: String,
        timeInMillis: Long
    ) {
        try {

            val alarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, AdhanReceiver::class.java).apply {
                putExtra("PRAYER_NAME", label)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                name.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
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

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JavascriptInterface
    fun cancelAllPrayers() {

        try {

            val alarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val prayers = listOf(
                "Fajr",
                "Dhuhr",
                "Asr",
                "Maghrib",
                "Isha"
            )

            for (prayer in prayers) {

                val intent =
                    Intent(this, AdhanReceiver::class.java)

                val pendingIntent =
                    PendingIntent.getBroadcast(
                        this,
                        prayer.hashCode(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or
                                PendingIntent.FLAG_IMMUTABLE
                    )

                alarmManager.cancel(pendingIntent)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
