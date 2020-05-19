package util

import java.util.*

class DateMessageProvider {
    val dateMessage: String
        get() {
            val now = Calendar.getInstance()
            val hour = now[Calendar.HOUR_OF_DAY]
            return if (hour < 12) {
                "오전"
            } else "오후"
        }
}