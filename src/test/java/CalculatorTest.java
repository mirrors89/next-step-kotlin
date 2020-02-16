import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {


    @Test
    public void add() {
        Calculator calculator = new Calculator();
        assertEquals(9, calculator.add(6, 3));

    }
    @Test
    public void subtract() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.subtract(6, 3));
    }

    @Test
    public void multiply() {
        Calculator calculator = new Calculator();
        assertEquals(12, calculator.multiply(2, 6));
    }

    @Test
    public void divide() {
        Calculator calculator = new Calculator();
        assertEquals(2, calculator.divide(8, 4));
    }

}
