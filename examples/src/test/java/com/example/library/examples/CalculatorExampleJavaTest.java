package com.example.library.examples;

import com.example.library.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Java test for the Calculator example using AssertJ.
 */
public class CalculatorExampleJavaTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculateSumOfNumbers() {
        // Sample list of numbers to process
        List<Double> numbers = Arrays.asList(10.0, 5.0, 7.5, 3.2, 9.8, 6.4);

        // Calculate the sum of all numbers
        double sum = 0.0;
        for (Double number : numbers) {
            sum = calculator.add(sum, number);
        }

        // Verify the sum using AssertJ
        assertThat(sum).isEqualTo(41.9);

        // Calculate the average
        double average = calculator.divide(sum, numbers.size());

        // Verify the average
        assertThat(average).isEqualTo(41.9 / 6.0);
    }

    @Test
    public void testCalculateSumOfSquares() {
        // Sample list of numbers to process
        List<Double> numbers = Arrays.asList(10.0, 5.0, 7.5, 3.2, 9.8, 6.4);

        // Calculate the sum of squares
        double sumOfSquares = 0.0;
        for (Double number : numbers) {
            double square = calculator.power(number, 2.0);
            sumOfSquares = calculator.add(sumOfSquares, square);
        }

        // Verify the sum of squares
        double expected = 10.0*10.0 + 5.0*5.0 + 7.5*7.5 + 3.2*3.2 + 9.8*9.8 + 6.4*6.4;
        assertThat(sumOfSquares).isEqualTo(expected);
    }

    @Test
    public void testBasicOperations() {
        // Test addition
        assertThat(calculator.add(5.0, 3.0)).isEqualTo(8.0);

        // Test subtraction
        assertThat(calculator.subtract(10.0, 4.0)).isEqualTo(6.0);

        // Test multiplication
        assertThat(calculator.multiply(7.0, 6.0)).isEqualTo(42.0);

        // Test division
        assertThat(calculator.divide(20.0, 5.0)).isEqualTo(4.0);

        // Test square root
        assertThat(calculator.sqrt(16.0)).isEqualTo(4.0);

        // Test power
        assertThat(calculator.power(2.0, 3.0)).isEqualTo(8.0);
    }

    @Test
    public void testExceptionHandling() {
        // Test division by zero
        assertThatThrownBy(() -> calculator.divide(10.0, 0.0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Cannot divide by zero");

        // Test square root of negative number
        assertThatThrownBy(() -> calculator.sqrt(-1.0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Cannot calculate square root of a negative number");
    }
}
