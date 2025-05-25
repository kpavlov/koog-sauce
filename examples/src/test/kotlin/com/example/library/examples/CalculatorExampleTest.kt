package com.example.library.examples

import com.example.library.Calculator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.json.doubleOrNull

class CalculatorExampleTest : FunSpec({

    test("calculate sum of numbers") {
        // Sample list of numbers to process
        val numbers = listOf(10.0, 5.0, 7.5, 3.2, 9.8, 6.4)

        // Create a calculator instance
        val calculator = Calculator()

        // Calculate the sum of all numbers
        var sum = 0.0
        for (number in numbers) {
            sum = calculator.add(sum, number)
        }

        // Verify the sum
        sum shouldBe 41.9

        // Calculate the average
        val average = calculator.divide(sum, numbers.size.toDouble())

        // Verify the average
        average shouldBe 41.9 / 6.0
    }

    test("calculate sum of squares") {
        // Sample list of numbers to process
        val numbers = listOf(10.0, 5.0, 7.5, 3.2, 9.8, 6.4)

        // Create a calculator instance
        val calculator = Calculator()

        // Calculate the sum of squares
        var sumOfSquares = 0.0
        for (number in numbers) {
            val square = calculator.power(number, 2.0)
            sumOfSquares = calculator.add(sumOfSquares, square)
        }

        // Verify the sum of squares
        val expected = 10.0*10.0 + 5.0*5.0 + 7.5*7.5 + 3.2*3.2 + 9.8*9.8 + 6.4*6.4
        sumOfSquares shouldBe expected
    }

    test("test with mocks") {
        // Create a mock of the Calculator class
        val calculatorMock = mockk<Calculator>()

        // Define behavior for the mock
        every { calculatorMock.add(any(), any()) } answers { arg<Double>(0) + arg<Double>(1) }
        every { calculatorMock.power(any(), 2.0) } answers { arg<Double>(0) * arg<Double>(0) }

        // Sample list of numbers to process
        val numbers = listOf(10.0, 5.0, 7.5)

        // Calculate the sum of squares using the mock
        var sumOfSquares = 0.0
        for (number in numbers) {
            val square = calculatorMock.power(number, 2.0)
            sumOfSquares = calculatorMock.add(sumOfSquares, square)
        }

        // Verify the sum of squares
        val expected = 10.0*10.0 + 5.0*5.0 + 7.5*7.5
        sumOfSquares shouldBe expected

        // Verify that the methods were called the expected number of times
        verify(exactly = 3) { calculatorMock.power(any(), 2.0) }
        verify(exactly = 3) { calculatorMock.add(any(), any()) }
    }

    test("process numbers from JSON") {
        // Create a calculator instance
        val calculator = Calculator()

        // JSON string with numbers
        val jsonString = """[10.5, 20.3, 15.7, 8.2, 12.9]"""

        // Parse JSON string to JsonArray
        val jsonArray = kotlinx.serialization.json.Json.parseToJsonElement(jsonString) as kotlinx.serialization.json.JsonArray

        // Calculate sum of numbers from JSON
        var jsonSum = 0.0
        for (element in jsonArray) {
            val number = (element as? kotlinx.serialization.json.JsonPrimitive)?.doubleOrNull
            if (number != null) {
                jsonSum = calculator.add(jsonSum, number)
            }
        }

        // Verify the sum
        jsonSum shouldBe 67.6
    }

    test("test all calculator operations") {
        // Create a calculator instance
        val calculator = Calculator()

        // Test addition
        calculator.add(5.0, 3.0) shouldBe 8.0

        // Test subtraction
        calculator.subtract(10.0, 4.0) shouldBe 6.0

        // Test multiplication
        calculator.multiply(7.0, 6.0) shouldBe 42.0

        // Test division
        calculator.divide(20.0, 5.0) shouldBe 4.0

        // Test square root
        calculator.sqrt(16.0) shouldBe 4.0

        // Test power
        calculator.power(2.0, 3.0) shouldBe 8.0
    }
})
