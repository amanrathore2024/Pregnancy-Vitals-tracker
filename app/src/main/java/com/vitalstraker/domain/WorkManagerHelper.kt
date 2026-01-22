package com.vitalstraker.domain


import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun startReminder(context: Context) {

        val request = PeriodicWorkRequestBuilder<VitalReminderWorker>(
            5, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "vital_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
