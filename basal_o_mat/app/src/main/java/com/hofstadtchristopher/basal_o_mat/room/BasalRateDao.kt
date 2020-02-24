package com.hofstadtchristopher.basal_o_mat.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BasalRateDao {

    @Insert
    suspend fun insert(bRate: BasalRate)

    @Query("SELECT * from BasalRates ORDER BY ID ASC")
    fun getBasalRates(): LiveData<List<BasalRate>>

    @Query("SELECT * from BasalRates ORDER BY Name ASC")
    fun getBasalRatesNames(): LiveData<List<BasalRate>>

    @Query("SELECT * FROM BasalRates WHERE ID = :id")
    fun getBasalRate(id: Int): LiveData<List<BasalRate>>

    @Update(entity = BasalRate::class)
    suspend fun update(bRate: BasalRate)

    @Query("DELETE FROM BasalRates WHERE ID = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM BasalRates")
    suspend fun deleteAll()

    @Query("SELECT COUNT(ID) from BasalRates")
    suspend fun getSize(): Int
}