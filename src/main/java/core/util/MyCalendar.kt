package core.util

import java.util.*

class MyCalendar(private val calendar: Calendar) {

    fun getHour(): Hour {
        return Hour(calendar.get(Calendar.HOUR_OF_DAY))
    }
}