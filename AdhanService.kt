package com.muslimapp

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AdhanService : android.app.Service() {
    private var player: MediaPlayer? = null
    private val channelId = "adhan_channel"

    override fun onCreate() {
        super.onCreate()
        createChannel()
        val notification = Notification.Builder(this, channelId)
            .setContentTitle("المسلم الذكي")
            .setContentText("حان وقت الصلاة")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .build()
        startForeground(1001, notification)

        player = MediaPlayer.create(this, R.raw.adhan)
        player?.setOnCompletionListener { stopSelf() }
        player?.start()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(
                channelId,
                "أذان الصلاة",
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }
}
