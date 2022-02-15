package com.example.sleeplogger.sleepDetailsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.database.SleepInfo
import kotlinx.coroutines.launch

class SleepDetailsViewModel(sleepId: Int, private val dataSource: AppRepository) :
    ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private var _sleepLog: LiveData<SleepInfo?>
    val sleepLog
        get() = _sleepLog

    init {
        _sleepLog = getSleepLog(sleepId)
    }

    private fun getSleepLog(sleepId: Int): LiveData<SleepInfo?> {
        viewModelScope.launch {
            try {
                _sleepLog = dataSource.getOneSleepInfo(sleepId)
                _response.value = "Success: Sleep log retrieved"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
        return _sleepLog
    }
}