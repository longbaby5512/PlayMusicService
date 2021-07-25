package com.example.playmusicservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.playmusicservice.service.BackgroundService


class BootUpReceiver : BroadcastReceiver() {
/*    override fun onReceive(context: Context, intent: Intent) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    startActivityNotification(
                        context, 1111, context.resources.getString(
                            R.string.app_name
                        ), "App open boot"
                    )
                } else {
                    Toast.makeText(
                        context,
                        "BootDeviceReceiver onReceive start service directly.",
                        Toast.LENGTH_LONG
                    ).show()
                    val i = Intent(context, MainActivity::class.java)
                    i.action = Actions.START.name
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(i)
                }
            } catch (e: Exception) {
                Log.e("BootUpReceiver", e.message, e)
            }
    }

    private fun startActivityNotification(
        context: Context, notificationID: Int,
        title: String?, message: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                "START APP BOOT",
                "START APP BOOT",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This is channel"
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java).let {
            it.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "START APP BOOT")

        val notification = builder.setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // for under android 26 compatibility
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationID, notification)
    }*/

    companion object {
        private const val TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER"
    }
    override fun onReceive(context: Context, intent: Intent) {
/*        val action = intent.action
        val message = "BootDeviceReceiver onReceive, action is $action"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        startServiceByAlarm(context)*/
        intent.setClass(context, BackgroundService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intent)
        else
            context.startService(intent)
    }

    /* Create an repeat Alarm that will invoke the background service for each execution time.
     * The interval time can be specified by your self.  */
/*    private fun startServiceByAlarm(context: Context) {
        // Get alarm manager.
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Create intent to invoke the background service.
        val intent = Intent(context, PlaySongService::class.java)
        val pendingIntent =
            PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val startTime = System.currentTimeMillis()
        val intervalTime = (60 * 1000).toLong()
        val message = "Start service use repeat alarm. "
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        Log.d(TAG_BOOT_BROADCAST_RECEIVER, message)
        // Create repeat alarm.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent)
    }*/
}