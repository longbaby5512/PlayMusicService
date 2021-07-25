package com.example.playmusicservice

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationApp : Application() {
    companion object {
        fun showNotification(context: Context, message: String, channelId: String, id: Int) {
            val notification = createNotification(context, message, channelId)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(id, notification)
        }
        fun createNotification(context: Context, message: String, channelId: String): Notification {

            // depending on the Android API that we're dealing with we will have
            // to use a specific method to create the notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "This is channel"
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                }
                notificationManager.createNotificationChannel(channel)
            }

            val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java).let {
                PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)

            return builder
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // for under android 26 compatibility
                .build()
        }
    }
}