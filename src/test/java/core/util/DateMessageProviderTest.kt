package core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class DateMessageProviderTest {

    @Test
    fun 오전() {
        val provider = DateMessageProvider()
        assertEquals("오전", provider.getDateMessage(createCurrentDate(11)))
    }

    @Test
    fun 오후() {
        val provider = DateMessageProvider()
        assertEquals("오후", provider.getDateMessage(createCurrentDate(13)))
    }



    private fun createCurrentDate(hourOfDay: Int): Calendar {
        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, hourOfDay)
        return now
    }

}