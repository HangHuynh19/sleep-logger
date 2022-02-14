package com.example.sleeplogger.allSleepInfoScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.database.SleepInfo
import com.example.sleeplogger.loggerScreen.LoggerViewModel
import kotlinx.coroutines.flow.Flow

class AllSleepInfoViewModel(dataSource: AppRepository) : ViewModel() {
    val database = dataSource

    fun fullSleepLogs(): Flow<List<SleepInfo>> = database.getAllSleepInfo()
}

class AllSleepInfoViewModelFactory (private val dataSource: AppRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllSleepInfoViewModel::class.java)) {
            return AllSleepInfoViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}