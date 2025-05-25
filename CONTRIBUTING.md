<!-- Basic guidelines, should be refined -->

# Contributing Guidelines

One can contribute to the project by reporting issues or submitting changes via pull request.

## Reporting issues

Please use [GitHub issues](https://github.com/kpavlov/gradle-kotlin-kmp-library-template/issues) for filing feature requests and bug reports.

## Submitting changes

Submit pull requests [here](https://github.com/kpavlov/gradle-kotlin-kmp-library-template/pulls).
However, please keep in mind that maintainers will have to support the resulting code of the project,
so do familiarize yourself with the following guidelines.

<!-- TODO: discuss git flow -->
<!-- TODO: align coding conventions with what the team is actually using -->

* If you make any code changes:
    * Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html).
        * Use 4 spaces for indentation.
        * Do not use imports with '*'.
    * [Build the project](#building) to make sure it all works and passes the tests.
* If you fix a bug:
    * Write the test that reproduces the bug.
    * Fixes without tests are accepted only in exceptional circumstances if it can be shown that writing the
      corresponding test is too hard or otherwise impractical.
      * Follow the style of writing tests that is used in this project:
        name test functions as
        ```kotlin
          fun `Should do something`() = runTest {...}
        ```                                          
      * Use [kotest assertions](https://kotest.io/docs/assertions/assertions.html)

* Comment on the existing issue if you want to work on it. Ensure that the issue not only describes a problem but also describes a solution that has received positive feedback. Propose a solution if none has been suggested.

## Documentation

The documentation is published on ... . 

## Building

This library is built with Gradle, but we recommend using the provided Makefile for common tasks.

### Using the Makefile

* Run `make build` to build the project. It also runs all the tests.
* Run `make test` to run only the tests.
* Run `make format` to format the code using Spotless.
* Run `make lint` to run linting checks (Spotless, Detekt).
* Run `make clean` to clean the project.
* Run `make doc` to generate KDoc documentation for the lib project.
* Run `make help` to see all available commands.

### Using Gradle Directly

For more specific tasks, you can use Gradle commands directly:

* Run `gradle <module>:check` to test a specific module to speed up development.
* Run `gradle <module>:test` to run tests for a specific module.

You can import this project into IDEA, but you have to delegate build actions
to Gradle (in Preferences -> Build, Execution, Deployment -> Build Tools -> Gradle -> Build and run).
