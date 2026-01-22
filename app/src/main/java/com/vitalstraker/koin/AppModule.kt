package com.vitalstraker.koin

import androidx.room.Room
import com.vitalstraker.data.VitalRepository
import com.vitalstraker.data.local.VitalDao
import com.vitalstraker.data.local.VitalDatabase
import com.vitalstraker.presentation.VitalViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val AppModule = module{

    single {
        Room.databaseBuilder(
            androidContext(),
            VitalDatabase::class.java,
            name = "vital_db"
        ).build()
    }

    single<VitalDao> {
        get<VitalDatabase>().vitalDao()
    }

    single {
        VitalRepository(get())
    }

    viewModel { VitalViewModel(get()) }

}