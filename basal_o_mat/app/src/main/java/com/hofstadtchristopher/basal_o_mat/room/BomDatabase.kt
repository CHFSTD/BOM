package com.hofstadtchristopher.basal_o_mat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(BasalRate::class, TestResult::class), version = 1, exportSchema = false)
abstract class BomDatabase : RoomDatabase() {

    abstract fun basalRateDao() : BasalRateDao
    abstract fun testResultDao(): TestResultDao

    private class BomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.basalRateDao(), database.testResultDao())
                }
            }
        }

        suspend fun populateDatabase(basalRateDao: BasalRateDao, testResultDao: TestResultDao) {
            basalRateDao.deleteAll()
            testResultDao.deleteAll()
        }
    }

    companion object {
        //singleton
        @Volatile
        private var INSTANCE: BomDatabase?= null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : BomDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null) {
                return tmpInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BomDatabase::class.java,
                    "Basal-O-Mat_Database"
                )
                    .addCallback(BomDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}