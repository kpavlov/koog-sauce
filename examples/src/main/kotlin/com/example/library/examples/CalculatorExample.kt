package com.example.library.examples

import com.example.library.Calculator
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull

/**
 * Example application demonstrating the usage of the Calculator class.
 * This example processes a flow of numbers and aggregates a sum.
 */
fun main() {
    println("Calculator Example Application")
    println("==============================")

    // Create a calculator instance
    val calculator = Calculator()

    // Sample list of numbers to process
    val numbers = listOf(10.0, 5.0, 7.5, 3.2, 9.8, 6.4)
    println("Processing numbers: $numbers")

    // Calculate the sum of all numbers
    var sum = 0.0
    for (number in numbers) {
        sum = calculator.add(sum, number)
        println("Running sum after adding $number: $sum")
    }

    println("\nTotal sum: $sum")

    // Calculate the average
    val average = calculator.divide(sum, numbers.size.toDouble())
    println("Average: $average")

    // Calculate the sum of squares
    var sumOfSquares = 0.0
    for (number in numbers) {
        val square = calculator.power(number, 2.0)
        sumOfSquares = calculator.add(sumOfSquares, square)
        println("Square of $number: $square")
    }

    println("\nSum of squares: $sumOfSquares")

    // Demonstrate JSON processing
    println("\nProcessing numbers from JSON:")
    val jsonString = """[10.5, 20.3, 15.7, 8.2, 12.9]"""
    val jsonArray = Json.parseToJsonElement(jsonString) as JsonArray

    var jsonSum = 0.0
    for (element in jsonArray) {
        val number = (element as? JsonPrimitive)?.doubleOrNull
        if (number != null) {
            jsonSum = calculator.add(jsonSum, number)
            println("Added $number, running sum: $jsonSum")
        }
    }

    println("\nTotal sum from JSON: $jsonSum")

    // Demonstrate some other calculator operations
    println("\nOther calculator operations:")
    println("5 + 3 = ${calculator.add(5.0, 3.0)}")
    println("10 - 4 = ${calculator.subtract(10.0, 4.0)}")
    println("7 * 6 = ${calculator.multiply(7.0, 6.0)}")
    println("20 / 5 = ${calculator.divide(20.0, 5.0)}")
    println("√16 = ${calculator.sqrt(16.0)}")
    println("2³ = ${calculator.power(2.0, 3.0)}")

    println("\nExample completed successfully!")
}
