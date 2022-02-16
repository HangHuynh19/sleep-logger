package com.example.sleeplogger

import android.text.Editable
import kotlin.math.floor
import kotlin.math.roundToInt

fun modifyDateInputForDatabase(dayInput: Int, monthInput: Int, yearInput: Int): String {
    val date: String = if (dayInput < 10) "0${dayInput}" else "$dayInput"
    val month: String = if (monthInput < 10) "0${monthInput}" else "$monthInput"

    return "$yearInput-$month-$date"
}

fun convertTimeInputForDatabase(hour: String, minute: String) : Double {
    val convertedHour = hour.toInt()
    val convertedMinute = minute.toInt()

    return convertedHour + 1.00 * convertedMinute / 60
}

fun convertMinutesForDisplay(sleepDuration: Double) : Int {
    return sleepDuration.minus(floor(sleepDuration)).times(60).roundToInt()
}

fun convertDateForDisplay(dateString: String) : String {
    val strings: List<String> = dateString.split("-")

    return "${strings[2]}-${strings[1]}-${strings[0]}"
}