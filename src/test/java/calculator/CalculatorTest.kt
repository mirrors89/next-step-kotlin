package calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorTest {
    private var calculator: Calculator? = null
    @BeforeEach
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun add() {
        assertEquals(9, calculator!!.add(6, 3))
    }

    @Test
    fun subtract() {
        assertEquals(3, calculator!!.subtract(6, 3))
    }

    @Test
    fun multiply() {
        assertEquals(12, calculator!!.multiply(2, 6))
    }

    @Test
    fun divide() {
        assertEquals(2, calculator!!.divide(8, 4))
    }
}