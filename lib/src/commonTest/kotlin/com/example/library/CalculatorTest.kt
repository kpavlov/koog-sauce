package com.example.library

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

// Vibe-coded test
class CalculatorTest {
    private lateinit var calculator: Calculator

    @BeforeTest
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun `add should return the sum of two numbers`() {
        // Given
        val a = 5.0
        val b = 3.0

        // When
        val result = calculator.add(a, b)

        // Then
        result shouldBe 8.0
    }

    @Test
    fun `subtract should return the difference between two numbers`() {
        // Given
        val a = 10.0
        val b = 4.0

        // When
        val result = calculator.subtract(a, b)

        // Then
        result shouldBe 6.0
    }

    @Test
    fun `multiply should return the product of two numbers`() {
        // Given
        val a = 7.0
        val b = 6.0

        // When
        val result = calculator.multiply(a, b)

        // Then
        result shouldBe 42.0
    }

    @Test
    fun `divide should return the quotient of two numbers`() {
        // Given
        val a = 20.0
        val b = 5.0

        // When
        val result = calculator.divide(a, b)

        // Then
        result shouldBe 4.0
    }

    @Test
    fun `divide should throw IllegalArgumentException when dividing by zero`() {
        // Given
        val a = 10.0
        val b = 0.0

        // When/Then
        shouldThrow<IllegalArgumentException> {
            calculator.divide(a, b)
        }
    }

    @Test
    fun `sqrt should return the square root of a number`() {
        // Given
        val a = 16.0

        // When
        val result = calculator.sqrt(a)

        // Then
        result shouldBe 4.0
    }

    @Test
    fun `sqrt should throw IllegalArgumentException for negative numbers`() {
        // Given
        val a = -1.0

        // When/Then
        shouldThrow<IllegalArgumentException> {
            calculator.sqrt(a)
        }
    }

    @Test
    fun `power should return the base raised to the exponent`() {
        // Given
        val base = 2.0
        val exponent = 3.0

        // When
        val result = calculator.power(base, exponent)

        // Then
        result shouldBe 8.0
    }

    @Test
    fun `power should return 1 when exponent is 0`() {
        // Given
        val base = 5.0
        val exponent = 0.0

        // When
        val result = calculator.power(base, exponent)

        // Then
        result shouldBe 1.0
    }

    @Test
    fun `power should return reciprocal when exponent is negative`() {
        // Given
        val base = 2.0
        val exponent = -2.0

        // When
        val result = calculator.power(base, exponent)

        // Then
        result shouldBe 0.25 // 1/(2^2) = 1/4 = 0.25
    }

    @Test
    fun `power should work with non-integer exponents`() {
        // Given
        val base = 4.0
        val exponent = 0.5

        // When
        val result = calculator.power(base, exponent)

        // Then
        result shouldBe 2.0 // sqrt(4) = 2.0
    }
}
