# Kotlin Multiplatform Library Template

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Target JVM](https://img.shields.io/badge/Target%20JDK-17-green.svg)](https://jdk.java.net/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.1-green.svg)](https://gradle.org)
[![Build Status](https://img.shields.io/github/actions/workflow/status/yourusername/kotlin-kmp-library/build.yml?branch=main)](https://github.com/yourusername/kotlin-kmp-library/actions)
[![Documentation](https://img.shields.io/badge/Documentation-KDoc-blue)](https://yourusername.github.io/kotlin-kmp-library/)
[![Coverage](https://img.shields.io/badge/Coverage-30%25-yellow)](https://github.com/Kotlin/kotlinx-kover)

A template project for creating Kotlin Multiplatform libraries with comprehensive testing and documentation setup.

[![Buy me a Coffee](https://cdn.buymeacoffee.com/buttons/default-orange.png)](https://buymeacoffee.com/mailsk)

## Features

- **Kotlin Multiplatform** support for JVM, with extensibility for other platforms
- **Calculator** class implementation with basic arithmetic operations
- **Comprehensive testing** setup with:
  - Kotlin tests using [Kotest](https://kotest.io/)
  - Java tests using [AssertJ](https://assertj.github.io/doc/)
  - Mocking with [MockK](https://mockk.io/)
- **Code quality** tools:
  - [Detekt](https://detekt.github.io/detekt/) for static code analysis
  - [Spotless](https://github.com/diffplug/spotless) for code formatting
  - [Kover](https://github.com/Kotlin/kotlinx-kover) for code coverage
- **Documentation** generation with [Dokka](https://github.com/Kotlin/dokka)
- **Maven publication** setup for easy distribution
- **Example module** demonstrating library usage

## Project Structure

- **lib**: The main library module containing the Kotlin Multiplatform code
  - `src/commonMain`: Common Kotlin code
  - `src/jvmMain`: JVM-specific code
  - `src/commonTest`: Common tests
  - `src/jvmTest`: JVM-specific tests
- **examples**: Module demonstrating usage of the library
  - Contains both Kotlin and Java examples and tests

## Requirements

- JDK 21 or higher
- Gradle 8.7 or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/kotlin-kmp-library.git
cd kotlin-kmp-library
```

### Build the Project

```bash
./gradlew build
```

Or using the Makefile:

```bash
make build
```

## Usage

### Basic Usage

```kotlin
// Assuming Calculator is imported from com.example.library
val calculator = Calculator()

// Basic operations
println("5 + 3 = ${calculator.add(5.0, 3.0)}")
println("10 - 4 = ${calculator.subtract(10.0, 4.0)}")
println("7 * 6 = ${calculator.multiply(7.0, 6.0)}")
println("20 / 5 = ${calculator.divide(20.0, 5.0)}")
println("√16 = ${calculator.sqrt(16.0)}")
println("2³ = ${calculator.power(2.0, 3.0)}")
```

See the `examples` module for more detailed usage examples.

## Development

### Available Make Commands

- `make build`: Build the project
- `make test`: Run tests
- `make format`: Format the code using Spotless
- `make lint`: Run linting checks (Spotless, Detekt)
- `make clean`: Clean the project
- `make publish`: Publish to Maven Local
- `make doc`: Generate KDoc documentation for the lib project
- `make help`: Show help message

### Running Tests

```bash
./gradlew test
```

Or using the Makefile:

```bash
make test
```

### Generating Documentation

```bash
./gradlew :lib:dokkaGeneratePublicationHtml
```

Or using the Makefile:

```bash
make doc
```

The documentation is automatically generated and published to GitHub Pages when changes are pushed to the main branch. You can access the latest documentation at:

https://yourusername.github.io/kotlin-kmp-library/

### Publishing to Maven Local

```bash
./gradlew publishToMavenLocal
```

Or using the Makefile:

```bash
make publish
```

## Customizing the Template

1. Update the group ID and version in `gradle.properties`
2. Modify the library name in `settings.gradle.kts`
3. Update the package names in the source files
4. Replace the Calculator implementation with your own code
5. Update the documentation links in `dokka-convention.gradle.kts`

## License

This project is licensed under the MIT License - see the LICENSE file for details.
