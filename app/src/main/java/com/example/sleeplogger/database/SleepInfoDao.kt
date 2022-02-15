package com.example.sleeplogger.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sleepInfo: SleepInfo)

    @Query("UPDATE sleep_info SET sleep_duration = :sleepDuration, sleep_quality = :sleepQuality, time_added = :timeAdded WHERE date_recorded = :dateInserted")
    suspend fun update(dateInserted: String, sleepDuration: Double, sleepQuality: Int, timeAdded: Long)

    @Query("SELECT * FROM sleep_info ORDER BY date_recorded desc")
    fun getAllSleepInfo() : Flow<List<SleepInfo>>

    @Query("SELECT * FROM sleep_info WHERE sleepId = :key")
    fun getOneSleepInfo(key: Int) : LiveData<SleepInfo?>

    @Query("DELETE FROM sleep_info WHERE sleepId = :key")
    fun deleteOneSleepInfo(key: Int)

    @Query("SELECT EXISTS (SELECT * FROM sleep_info WHERE date_recorded = :dateRecorded)")
    suspend fun isRowExist(dateRecorded: String) : Boolean
}