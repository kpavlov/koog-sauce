package com.example.library

/**
 * A simple calculator class that provides basic arithmetic operations.
 */
public class Calculator {
    /**
     * Adds two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The sum of the two numbers.
     */
    public fun add(a: Double, b: Double): Double = a + b

    /**
     * Subtracts the second number from the first.
     *
     * @param a The first number.
     * @param b The second number to subtract from the first.
     * @return The difference between the two numbers.
     */
    public fun subtract(a: Double, b: Double): Double = a - b

    /**
     * Multiplies two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The product of the two numbers.
     */
    public fun multiply(a: Double, b: Double): Double = a * b

    /**
     * Divides the first number by the second.
     *
     * @param a The dividend.
     * @param b The divisor.
     * @return The quotient of the division.
     * @throws IllegalArgumentException if the divisor is zero.
     */
    public fun divide(a: Double, b: Double): Double {
        require(b != 0.0) { "Cannot divide by zero" }
        return a / b
    }

    /**
     * Calculates the square root of a number.
     *
     * @param a The number to calculate the square root of.
     * @return The square root of the number.
     * @throws IllegalArgumentException if the number is negative.
     */
    public fun sqrt(a: Double): Double {
        require(a >= 0.0) { "Cannot calculate square root of a negative number" }
        return kotlin.math.sqrt(a)
    }

    /**
     * Calculates the power of a number.
     *
     * @param base The base number.
     * @param exponent The exponent.
     * @return The base raised to the power of the exponent.
     */
    public fun power(base: Double, exponent: Double): Double {
        // Simple implementation for integer exponents
        if (exponent == 0.0) return 1.0
        if (exponent < 0) return 1.0 / power(base, -exponent)

        // For integer exponents, use multiplication
        if (exponent % 1.0 == 0.0) {
            var result = 1.0
            repeat(exponent.toInt()) {
                result *= base
            }
            return result
        }

        // For non-integer exponents, use the identity e^(exponent * ln(base))
        return kotlin.math.exp(exponent * kotlin.math.ln(base))
    }
}
