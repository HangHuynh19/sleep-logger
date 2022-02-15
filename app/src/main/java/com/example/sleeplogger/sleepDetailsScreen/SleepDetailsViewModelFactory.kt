package com.example.sleeplogger.sleepDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sleeplogger.database.AppRepository

class SleepDetailsViewModelFactory (private val sleepId: Int, private val dataSource: AppRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailsViewModel::class.java)) {
            return SleepDetailsViewModel(sleepId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}