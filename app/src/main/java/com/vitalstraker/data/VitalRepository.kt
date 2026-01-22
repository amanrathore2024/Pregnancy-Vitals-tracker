package com.vitalstraker.data

import com.vitalstraker.data.local.VitalDao
import com.vitalstraker.data.local.model.VitalItem
import kotlinx.coroutines.flow.Flow

class VitalRepository(private val dao: VitalDao) {

    suspend fun insert(vital: VitalItem) {
        dao.insertVital(vital)
    }

    fun getAllVitals(): Flow<List<VitalItem>> {
        return dao.getAllVitals()
    }

    suspend fun clear() {
        dao.clearAll()
    }
}
