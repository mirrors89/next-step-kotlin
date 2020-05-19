package util

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class DateMessageProviderTest {

    @Test
    fun 오전() {
        val provider = DateMessageProvider()
        assertEquals("오전", provider.dateMessage)
    }


    @Test
    fun 오후() {
        val provider = DateMessageProvider()
        assertEquals("오후", provider.dateMessage)
    }
}