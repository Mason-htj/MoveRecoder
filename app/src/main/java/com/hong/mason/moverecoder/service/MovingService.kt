package com.hong.mason.moverecoder.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.util.TimeFormatUtils
import android.app.NotificationChannel
import android.app.PendingIntent
import android.os.Build
import com.hong.mason.moverecoder.common.RequestCodes
import com.hong.mason.moverecoder.view.MainActivity


class MovingService : IntentService("MovingService") {
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = CHANNEL_MOVING
            val description = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_MOVING, name, importance)
            mChannel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            RequestCodes.ACTION_START -> {
                val noti = NotificationCompat.Builder(baseContext, CHANNEL_MOVING)
                        .setSmallIcon(R.drawable.ic_moving_black_24dp)
                        .setContentTitle("You are moving")
                        .setContentText(TimeFormatUtils.getDateString(System.currentTimeMillis()))
                        .setOngoing(true)
                        .setContentIntent(createAppPendingIntent())
                        .addAction(createArriveAction())
                        .addAction(createCancelAction())
                        .build()
                notificationManager.notify(ID_MOVING, noti)
            }
            RequestCodes.ACTION_ARRIVE, RequestCodes.ACTION_CANCEL -> {
                notificationManager.cancel(ID_MOVING)
            }
        }
    }

    private fun createArriveAction(): NotificationCompat.Action {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, RequestCodes.MOVING_ARRIVE, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action(R.drawable.ic_arrive_black_24dp, "Arrive", pendingIntent)
    }

    private fun createCancelAction(): NotificationCompat.Action {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, RequestCodes.MOVING_CANCEL, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action(R.drawable.ic_arrive_black_24dp, "Cancel", pendingIntent)
    }

    private fun createAppPendingIntent(): PendingIntent {
        return PendingIntent.getActivity(this, -1, Intent(this, MainActivity::class.java), PendingIntent.FLAG_ONE_SHOT)
    }

    companion object {
        const val ID_MOVING = 0x101
        const val CHANNEL_MOVING = "CHANNEL_MOVING"
    }
}