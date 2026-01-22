package com.vitalstraker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vitalstraker.data.local.model.VitalItem

@Database(
    entities = [VitalItem::class],
    version = 1,
    exportSchema = false
)
abstract class VitalDatabase : RoomDatabase() {

    abstract fun vitalDao(): VitalDao
}