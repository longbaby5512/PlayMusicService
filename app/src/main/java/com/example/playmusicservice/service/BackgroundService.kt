package com.example.playmusicservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.playmusicservice.NotificationApp

class BackgroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        val notificationChannelId = "APP BOOT"
        val notification =
            NotificationApp.createNotification(this, "App started", notificationChannelId)
        startForeground(9999, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}