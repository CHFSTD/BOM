package com.hofstadtchristopher.basal_o_mat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(BasalRate::class), version = 1, exportSchema = false)
abstract class BomDatabase : RoomDatabase() {

    abstract fun basalRateDao() : BasalRateDao

    private class BomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.basalRateDao())
                }
            }
        }

        suspend fun populateDatabase(basalRateDao: BasalRateDao) {
            basalRateDao.deleteAll() //TODO bei weiterem table dessen Dao und deleteAll etc hinzufügen

        }
    }

    companion object {
        //singleton
        @Volatile
        private var INSTANCE: BomDatabase?= null

        fun getDatabase(
            context: Context,
            scrope: CoroutineScope
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
                    .addCallback(BomDatabaseCallback(scrope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}