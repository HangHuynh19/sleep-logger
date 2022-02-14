package com.example.sleeplogger.database

import kotlinx.coroutines.flow.Flow

object AppRepository {
    val sleepInfoDao = SleepDatabase.getInstance().sleepInfoDao

    suspend fun insertSleepInfo(sleepInfo: SleepInfo) = sleepInfoDao.insert(sleepInfo)

    fun getAllSleepInfo() : Flow<List<SleepInfo>> = sleepInfoDao.getAllSleepInfo()

    fun getOneSleepInfo(sleepId: Long) = sleepInfoDao.getOneSleepInfo(sleepId)

    suspend fun updateSleepInfo(dateRecorded: String,
                                sleepDuration: Double,
                                sleepQuality: Int,
                                timeAdded: Long) =
        sleepInfoDao.update(dateRecorded, sleepDuration, sleepQuality, timeAdded)

    suspend fun deleteSleepInfo(sleepId: Long) = sleepInfoDao.deleteOneSleepInfo(sleepId)

    suspend fun isRowExist(dateRecorded: String) = sleepInfoDao.isRowExist(dateRecorded)
}