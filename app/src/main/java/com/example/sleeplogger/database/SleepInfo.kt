package com.example.sleeplogger.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_info")
data class SleepInfo(
    @PrimaryKey(autoGenerate = true)
    var sleepId: Int = 0,
    @ColumnInfo(name = "date_recorded")
    var dateRecorded: String = "",
    @ColumnInfo(name = "sleep_duration")
    var sleepDuration: Double = 0.00,
    @ColumnInfo(name = "sleep_quality")
    var sleepQuality: Int = -1,
    @ColumnInfo(name = "time_added")
    val timeAdded: Long = System.currentTimeMillis(),
) {
    constructor(dateRecorded: String, sleepDuration: Double, sleepQuality: Int, timeAdded: Long) : this(
        0,
        dateRecorded,
        sleepDuration,
        sleepQuality,
        timeAdded
    )
}