package com.example.playmusicservice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.playmusicservice.NotificationApp


class AppInstalledReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_CHANGED) {
            NotificationApp.showNotification(context, "App Installed", "CHECK APP", 0)
        }
    }
}