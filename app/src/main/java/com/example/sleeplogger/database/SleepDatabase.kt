package com.example.sleeplogger.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sleeplogger.MyApp

@Database(entities = [SleepInfo::class], version = 2, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {
    abstract val sleepInfoDao : SleepInfoDao

    companion object {
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(): SleepDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        MyApp.appContext,
                        SleepDatabase::class.java,
                        "sleep_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}