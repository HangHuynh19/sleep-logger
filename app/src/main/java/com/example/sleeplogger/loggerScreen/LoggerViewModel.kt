package com.example.sleeplogger.loggerScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.database.SleepInfo
import kotlinx.coroutines.launch

class LoggerViewModel(sleepId: Int, dataSource: AppRepository) : ViewModel() {
    val database = dataSource

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private lateinit var _sleepLog: LiveData<SleepInfo?>
    val sleepLog
        get() = _sleepLog

    private fun getSleepLog(sleepId: Int): LiveData<SleepInfo?> {
        viewModelScope.launch {
            try {
                _sleepLog = database.getOneSleepInfo(sleepId)
                _response.value = "Success: Sleep log retrieved"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
        return _sleepLog
    }

    fun onSendButtonClicked(dateRecorded: String, sleepDuration: Double, sleepQuality: Int, timeAdded: Long) {
        viewModelScope.launch {
            if (database.isRowExist(dateRecorded)) {
                database.updateSleepInfo(dateRecorded, sleepDuration, sleepQuality, timeAdded)
            } else {
                database.insertSleepInfo(SleepInfo(dateRecorded, sleepDuration, sleepQuality, timeAdded))
            }
        }
    }

    init {
        _sleepLog = getSleepLog(sleepId)
    }

}