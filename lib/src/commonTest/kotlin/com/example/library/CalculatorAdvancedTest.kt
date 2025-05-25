package com.example.library

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class CalculatorAdvancedTest {

    @Test
    fun `test calculator with mockk`() {
        // Create a mock of the Calculator class
        val calculatorMock = mockk<Calculator>()

        // Define behavior for the mock
        every { calculatorMock.add(5.0, 3.0) } returns 8.0
        every { calculatorMock.multiply(any(), any()) } answers { arg<Double>(0) * arg<Double>(1) }

        // Use the mock
        val addResult = calculatorMock.add(5.0, 3.0)
        val multiplyResult = calculatorMock.multiply(4.0, 2.0)

        // Verify results
        addResult shouldBe 8.0
        multiplyResult shouldBe 8.0

        // Verify that methods were called with specific parameters
        verify(exactly = 1) { calculatorMock.add(5.0, 3.0) }
        verify(exactly = 1) { calculatorMock.multiply(4.0, 2.0) }
    }

    @Test
    fun `test calculator`() {
        // Create a real calculator
        val calculator = Calculator()

        // Use power-assert to verify complex expressions
        // Note: Power-assert transforms assertions at compile time to provide more detailed error messages
        val a = 5.0
        val b = 3.0
        val c = 2.0

        // This will show the values of all variables and expressions if it fails
        (calculator.add(a, b) - calculator.multiply(c, c)) shouldBe 4.0

        // Test a more complex calculation
        val result = calculator.add(
            calculator.multiply(a, b),
            calculator.divide(b, c)
        )

        result shouldBe 16.5
    }
}
