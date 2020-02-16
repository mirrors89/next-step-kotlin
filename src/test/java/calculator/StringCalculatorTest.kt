package calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.lang.RuntimeException
import kotlin.test.assertFailsWith

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
        assertEquals(3, stringCalculator!!.add("1,2"))
    }

    @Test
    @DisplayName("add 쉼표 또는 콜론 구분자")
    fun add_comma_or_colon_separator() {
        assertEquals(6, stringCalculator!!.add("1,2:3"))
    }

    @Test
    @DisplayName("add custom 구분자")
    fun add_custom_separator() {
        assertEquals(6, stringCalculator!!.add("//;\n1;2;3"))
    }

    @Test
    @DisplayName("add 음수를 전달하면 RuntimeExecption")
    fun add_negative() {
        assertThrows(RuntimeException::class.java) {
            stringCalculator!!.add("-1,2,3")
        }
    }
}