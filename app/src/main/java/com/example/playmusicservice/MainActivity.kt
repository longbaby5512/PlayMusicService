package com.example.playmusicservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.playmusicservice.NotificationApp.Companion.showNotification
import com.example.playmusicservice.service.BackgroundService
import com.example.playmusicservice.service.PlaySongService


class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action == Intent.ACTION_SCREEN_ON) {
                    showDialog()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showNotification(this, "App is running", "START APP", 1)
        requestPermission()


        Intent(this, BackgroundService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
                return@also
            }
            startService(it)
        }

        playButton = findViewById(R.id.btn_play)
        stopButton = findViewById(R.id.btn_stop)

        playButton.setOnClickListener {
            Intent(this, PlaySongService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(it)
                    return@also
                }
                startService(it)
            }
        }
        stopButton.setOnClickListener {
            Intent(this, PlaySongService::class.java).also {
                stopService(it)
            }
        }

        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(screenReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenReceiver)
        Intent(this, BackgroundService::class.java).also {
            stopService(it)
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Screen On")
            .setMessage("Screen turned on")
            .setPositiveButton("OK") { dialog, _ -> dialog.cancel() }
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }

    private fun requestPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.packageName)
            )
            startActivityForResult(intent, 2323)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2323) {
            if (Settings.canDrawOverlays(this)) {
                // You have permission
            }
        }
    }

}