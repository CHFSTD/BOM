package com.hofstadtchristopher.basal_o_mat.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BasalRateDao {

    @Insert
    suspend fun insert(bRate: BasalRate)

    @Query("SELECT * from BasalRates ORDER BY Name ASC")
    fun getBasalRates(): LiveData<List<BasalRate>>

    @Query("SELECT * FROM BasalRates WHERE ID = :id")
    fun getBasalRate(id: Int): LiveData<List<BasalRate>>

    @Update(entity = BasalRate::class)
    suspend fun update(bRate: BasalRate)

    @Delete(entity = BasalRate::class)
    suspend fun delete(bRate: BasalRate)
}