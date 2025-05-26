# Makefile for Kotlin Multiplatform Library

# Default task
.PHONY: all
all: build

# Build the project
.PHONY: build
build:
	./gradlew build

# Format the code
.PHONY: format
format:
	./gradlew spotlessApply

# Lint the code
.PHONY: lint
lint:
	./gradlew spotlessCheck detekt

# Publish to Maven Local
.PHONY: publish
publish:
	./gradlew publishToMavenLocal

# Run tests
.PHONY: test
test:
	./gradlew test

# Clean the project
.PHONY: clean
clean:
	./gradlew clean

# Generate documentation
.PHONY: doc
doc:
	./gradlew dokkaGeneratePublicationHtml

# Help
.PHONY: help
help:
	@echo "Available targets:"
	@echo "  all      - Default target, builds the project"
	@echo "  build    - Build the project"
	@echo "  format   - Format the code using Spotless"
	@echo "  lint     - Run linting checks (Spotless, Detekt)"
	@echo "  publish  - Publish to Maven Local"
	@echo "  test     - Run tests"
	@echo "  clean    - Clean the project"
	@echo "  doc      - Generate KDoc documentation for the lib project"
	@echo "  help     - Show this help message"
