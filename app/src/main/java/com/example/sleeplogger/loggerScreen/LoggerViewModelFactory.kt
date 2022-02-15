package com.example.sleeplogger.loggerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleeplogger.database.AppRepository

class LoggerViewModelFactory (private val sleepId: Int, private val dataSource: AppRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoggerViewModel::class.java)) {
            return LoggerViewModel(sleepId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}