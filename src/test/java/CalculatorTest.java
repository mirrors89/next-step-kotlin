import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void add() {
        assertEquals(9, calculator.add(6, 3));

    }
    @Test
    public void subtract() {
        assertEquals(3, calculator.subtract(6, 3));
    }

    @Test
    public void multiply() {
        assertEquals(12, calculator.multiply(2, 6));
    }

    @Test
    public void divide() {
        assertEquals(2, calculator.divide(8, 4));
    }

}
