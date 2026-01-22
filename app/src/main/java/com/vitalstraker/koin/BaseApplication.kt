package com.vitalstraker.koin


import android.app.Application
import com.vitalstraker.domain.WorkManagerHelper
import com.vitalstraker.domain.utils.NotificationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(AppModule)
        }

        NotificationUtil.createChannel(this)
        CoroutineScope(Dispatchers.Default).launch {
            WorkManagerHelper.startReminder(this@BaseApplication)
        }
    }
}