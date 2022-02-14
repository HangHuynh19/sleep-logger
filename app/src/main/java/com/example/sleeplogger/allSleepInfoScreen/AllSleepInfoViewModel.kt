package com.example.sleeplogger.allSleepInfoScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.database.SleepInfo

class AllSleepInfoViewModel(dataSource: AppRepository) : ViewModel() {
    val database = dataSource

    private var _allSleepInfo : LiveData<List<SleepInfo>>
    val allSleepInfo
        get() = _allSleepInfo

    init {
        _allSleepInfo = database.getAllSleepInfo()
        Log.i("sleepInfo VN", _allSleepInfo.toString())
    }

}