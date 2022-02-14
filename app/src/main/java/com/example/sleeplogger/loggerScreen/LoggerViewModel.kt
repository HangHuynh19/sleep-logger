package com.example.sleeplogger.loggerScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleeplogger.database.AppRepository
import com.example.sleeplogger.database.SleepInfo
import kotlinx.coroutines.launch

class LoggerViewModel(dataSource: AppRepository) : ViewModel() {
    val database = dataSource

    private val _navigateToAllSleepInfoFragment = MutableLiveData<Boolean?>()
    val navigateToAllSleepInfoFragment : MutableLiveData<Boolean?>
        get() = _navigateToAllSleepInfoFragment

    fun doneNavigating() {
        _navigateToAllSleepInfoFragment.value = null
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

}