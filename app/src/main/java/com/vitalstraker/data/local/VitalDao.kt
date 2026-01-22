package com.vitalstraker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitalstraker.data.local.model.VitalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertVital(vital: VitalItem)

    @Query("SELECT * FROM vital_table ORDER BY id DESC")
    fun getAllVitals(): Flow<List<VitalItem>>

    @Query("DELETE FROM vital_table")
    suspend fun clearAll()
}