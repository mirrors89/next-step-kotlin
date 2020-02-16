package calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class StringCalculatorTest {
    private var stringCalculator: StringCalculator? = null
    @BeforeEach
    fun setup() {
        stringCalculator = StringCalculator()
    }

    @Test
    @DisplayName("add null 또는 빈문자")
    fun add_null_or_empty() {
        assertEquals(0, stringCalculator!!.add(null))
        assertEquals(0, stringCalculator!!.add(""))
    }

    @Test
    @DisplayName("add 숫자하나")
    fun add_number_one() {
        assertEquals(1, stringCalculator!!.add("1"))
    }

    @Test
    @DisplayName("add 쉼표 구분자")
    fun add_comma_separator() {
        assertEquals(3, stringCalculator!!.add("1,2"));
    }
}