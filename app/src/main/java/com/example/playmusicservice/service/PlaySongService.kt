package com.example.playmusicservice.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.playmusicservice.NotificationApp
import com.example.playmusicservice.NotificationApp.Companion.createNotification
import com.example.playmusicservice.R

class PlaySongService : Service() {
    private lateinit var mediaPlayer : MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.mysong)
        mediaPlayer.isLooping = true

        NotificationApp.showNotification(this, "Start music", "PLAY SONG", 1)

        val notificationChannelId = "PLAY MUSIC SERVICE CHANNEL"
        val notification = createNotification(this, "Music is playing", notificationChannelId)
        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PlaySongService","onStartCommand executed with startId: $startId")
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("PlaySongService", "The service has been destroyed")
        NotificationApp.showNotification(this, "Stop music", "STOP SONG", 1)
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
        mediaPlayer.release()
        stopForeground(true)
        super.onDestroy()
    }

}