package com.hofstadtchristopher.basal_o_mat.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TestResultDao {
    @Insert
    suspend fun insert(tResult: TestResult)

    @Query("SELECT * from TestResults ORDER BY ID DESC")
    fun getTestResults(): LiveData<List<TestResult>>

    @Query("DELETE FROM TestResults WHERE ID = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM TestResults")
    suspend fun deleteAll()
}