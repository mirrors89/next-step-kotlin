package util

import java.util.*

class DateMessageProvider {
    fun getDateMessage(now: Calendar): String {
        val hour = now[Calendar.HOUR_OF_DAY]
        return if (hour < 12) {
            "오전"
        } else "오후"
    }
}