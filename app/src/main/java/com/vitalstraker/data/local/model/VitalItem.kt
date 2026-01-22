package com.vitalstraker.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vital_table")
data class VitalItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val heartRate: String,
    val bp: String,
    val weight: String,
    val kicks: String,
    val dateTime: String
)

