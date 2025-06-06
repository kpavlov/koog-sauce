# Koog-sauce — The Finishing Touch

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Target JVM](https://img.shields.io/badge/Target%20JVM-17-green.svg)](https://jdk.java.net/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.1-green.svg)](https://gradle.org)

[![Build](https://github.com/kpavlov/koog-sauce/actions/workflows/build.yml/badge.svg)](https://github.com/kpavlov/koog-sauce/actions/workflows/build.yml?branch=main)
[![Documentation](https://img.shields.io/badge/Documentation-KDoc-blue)](https://kpavlov.github.io/koog-sauce/)

![logo-256x256.png](docs/logo-256x256.png)

**Koog-sauce** is a missing ingredient that connects [Koog](https://github.com/koog-ai/koog) with other frameworks.

## Features

- **Spring AI Integration** - Provides a Koog's `SpringAiLLMClient`, which uses Spring AI's [ChatClient](https://docs.spring.io/spring-ai/reference/api/chatclient.html) under the hood.

## Requirements

- JDK 17 or higher
- Gradle 8.14.1 or higher

## Getting Started

### Add Dependency

Add the dependency to your build.gradle.kts file:

```kotlin
dependencies {
    implementation("me.kpavlov:koog-sauce:0.1.0")
    implementation("ai.koog:koog:0.0.1") // Koog library
    implementation("org.springframework.ai:spring-ai-openai:1.0.0") // Spring AI OpenAI client
}
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
// Create Spring AI ChatClient
val chatClient = org.springframework.ai.chat.client.ChatClient.builder(
    org.springframework.ai.openai.OpenAiChatModel
        .builder()
        .openAiApi(
            org.springframework.ai.openai.api.OpenAiApi
                .builder()
                .apiKey("your-api-key")
                .build(),
        ).build(),
).build()

// Create SpringAiLLMClient
val llmClient = me.kpavlov.koog.sauce.spring.ai.chat.SpringAiLLMClient(chatClient)

// Build a prompt using Koog DSL
val prompt = ai.koog.prompt.dsl.Prompt.build("myPrompt") {
    system("You are a helpful assistant")
    user("Tell me about Kotlin Multiplatform")
}

// Define the model to use
val model = ai.koog.prompt.llm.LLModel(
    ai.koog.prompt.llm.LLMProvider.OpenAI, 
    "gpt-4.1-nano", 
    listOf(ai.koog.prompt.llm.LLMCapability.Completion)
)

// Execute the prompt
suspend fun executePrompt() {
    val responses = llmClient.execute(prompt, model)

    // Process the response
    val response = responses.first()
    println("Response: ${response.content}")
}
```

This example demonstrates how to:
1. Create a Spring AI ChatClient
2. Wrap it with SpringAiLLMClient to make it compatible with Koog
3. Build a prompt using Koog's DSL
4. Execute the prompt and process the response

## Development

### Available Make Commands

- `make build`: Build the project
- `make test`: Run tests
- `make clean`: Clean the project
- `make publish`: Publish to Maven Local
- `make doc`: Generate KDoc documentation
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
./gradlew dokkaGeneratePublicationHtml
```

Or using the Makefile:

```bash
make doc
```

The documentation is automatically generated and published to GitHub Pages when changes are pushed to the main branch. You can access the latest documentation at:

https://kpavlov.github.io/koog-sauce/`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

[![Buy me a Coffee](https://cdn.buymeacoffee.com/buttons/default-orange.png)](https://buymeacoffee.com/mailsk)
