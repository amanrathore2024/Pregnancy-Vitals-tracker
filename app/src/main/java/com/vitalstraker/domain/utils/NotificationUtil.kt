package com.vitalstraker.domain.utils


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

object NotificationUtil {

    const val CHANNEL_ID = "vital_reminder_channel"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Vital Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminds user to log pregnancy vitals"
        }

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
