import org.junit.jupiter.api.Test;

public class CalculatorTest {


    @Test
    public void add() {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(3, 4));

    }
    @Test
    public void subtract() {
        Calculator calculator = new Calculator();

        System.out.println(calculator.subtract(5, 4));
    }

    @Test
    public void multiply() {
        Calculator calculator = new Calculator();

        System.out.println(calculator.multiply(2, 6));
    }

    @Test
    public void divide() {
        Calculator calculator = new Calculator();

        System.out.println(calculator.divide(8, 4));
    }

}
