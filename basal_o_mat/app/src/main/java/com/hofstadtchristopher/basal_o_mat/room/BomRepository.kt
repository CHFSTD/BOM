package com.hofstadtchristopher.basal_o_mat.room

import android.util.Log
import androidx.lifecycle.LiveData

class BomRepository(
    private val basalRateDao: BasalRateDao,
    private val testResultDao: TestResultDao
    ) {

    var bRateList: LiveData<List<BasalRate>> = basalRateDao.getBasalRates()
    var tResultList: LiveData<List<TestResult>> = testResultDao.getTestResults()

    suspend fun insert(bRate: BasalRate) {
        basalRateDao.insert(bRate)
        Log.i("RoomDB", "Basalrate: ${bRate.name} with id: ${bRate.id} added")
    }

    fun getAllBRates(): LiveData<List<BasalRate>> {
        bRateList = basalRateDao.getBasalRates()
        return  bRateList
    }

    fun getBasalRatesOrderedByNames(): LiveData<List<BasalRate>> {
        bRateList = basalRateDao.getBasalRatesNames()
        return bRateList
    }

    fun getBasalRate(id: Int): LiveData<List<BasalRate>> {
        return basalRateDao.getBasalRate(id)
    }

    suspend fun update(bRate: BasalRate) {
        basalRateDao.update(bRate)
        Log.i("RoomDB", "Basalrate: ${bRate.name} with id: ${bRate.id} updated to {$bRate}")
    }

    suspend fun deleteBRate(id: Int) {
        basalRateDao.delete(id)
        Log.i("RoomDB", "Basalrate with id: ${id} deleted")
    }

    suspend fun getSize(): Int {
        return basalRateDao.getSize()
    }

    suspend fun insert(tResult: TestResult) {
        testResultDao.insert(tResult)
        Log.i("RoomDB", "Test result from ${tResult.testDate} with id: ${tResult.id} added")
    }

    fun getTestResults(): LiveData<List<TestResult>> {
        tResultList = testResultDao.getTestResults()
        return tResultList
    }

    suspend fun deleteTResult(id: Int) {
        testResultDao.delete(id)
        Log.i("RoomDB", "Test result with id: ${id} deleted")
    }
}