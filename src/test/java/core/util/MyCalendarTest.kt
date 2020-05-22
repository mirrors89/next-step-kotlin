package core.util

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import java.util.*

internal class MyCalendarTest {

    @Test
    fun getHour() {

        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, 11)
        val myCalendar = MyCalendar(now)
        assertEquals(Hour(11), myCalendar.getHour())
    }
}