package com.hofstadtchristopher.basal_o_mat.room

import android.util.Log
import androidx.lifecycle.LiveData

class BomRepository(private val basalRateDao: BasalRateDao) {
    var bRateList: LiveData<List<BasalRate>> = basalRateDao.getBasalRates()

    suspend fun insert(bRate: BasalRate) {
        basalRateDao.insert(bRate)
        Log.i("RoomDB", "Basalrate: ${bRate.name} with id: ${bRate.id} added")
    }

    fun getAll(): LiveData<List<BasalRate>> {
        bRateList = basalRateDao.getBasalRates()
        return  bRateList
    }

    fun getBasalRate(id: Int): LiveData<List<BasalRate>> {
        return basalRateDao.getBasalRate(id)
    }

    suspend fun update(bRate: BasalRate) {
        basalRateDao.update(bRate)
        Log.i("RoomDB", "Basalrate: ${bRate.name} with id: ${bRate.id} updated to {$bRate}")
    }

    suspend fun delete(bRate: BasalRate) {
        basalRateDao.delete(bRate)
        Log.i("RoomDB", "Basalrate: ${bRate.name} with id: ${bRate.id} deleted")
    }
}