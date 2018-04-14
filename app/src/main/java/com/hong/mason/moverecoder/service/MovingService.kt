package com.hong.mason.moverecoder.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.util.TimeFormatUtils

class MovingService(name: String) : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        when (intent?.action) {
            ACTION_START -> {
                val noti = NotificationCompat.Builder(baseContext, CHANNEL_MOVING)
                        .setSmallIcon(R.drawable.ic_moving_black_24dp)
                        .setContentTitle("You are moving")
                        .setContentText(TimeFormatUtils.getDurationString(System.currentTimeMillis()))
                        .build()
                manager.notify(ID_MOVING, noti)
            }
            ACTION_ARRIVE, ACTION_CANCEL -> {
                manager.cancel(ID_MOVING)
            }
        }
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_ARRIVE = "ACTION_ARRIVE"
        const val ACTION_CANCEL = "ACTION_CANCEL"
        const val ID_MOVING = 0x101
        const val CHANNEL_MOVING = "CHANNEL_MOVING"
    }
}