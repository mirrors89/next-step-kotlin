package core.util

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class HourTest {

    @Test
    fun 오전() {
        val hour = Hour(11)
        assertEquals("오전", hour.getMessage())
    }

    @Test
    fun 오후() {
        val hour = Hour(16)
        assertEquals("오후", hour.getMessage())
    }
}