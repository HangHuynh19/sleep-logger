package com.example.sleeplogger.database

import kotlinx.coroutines.flow.Flow

object AppRepository {
    private val sleepInfoDao = SleepDatabase.getInstance().sleepInfoDao

    suspend fun insertSleepInfo(sleepInfo: SleepInfo) = sleepInfoDao.insert(sleepInfo)

    fun getAllSleepInfo() : Flow<List<SleepInfo>> = sleepInfoDao.getAllSleepInfo()

    fun getOneSleepInfo(sleepId: Int) = sleepInfoDao.getOneSleepInfo(sleepId)

    suspend fun updateSleepInfo(dateRecorded: String,
                                sleepDuration: Double,
                                sleepQuality: Int,
                                timeAdded: Long) =
        sleepInfoDao.update(dateRecorded, sleepDuration, sleepQuality, timeAdded)

    fun deleteSleepInfo(sleepId: Int) = sleepInfoDao.deleteOneSleepInfo(sleepId)

    suspend fun isRowExist(dateRecorded: String) = sleepInfoDao.isRowExist(dateRecorded)
}