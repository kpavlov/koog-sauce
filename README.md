# Koog-sauce — The Finishing Touch

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.0-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Target JVM](https://img.shields.io/badge/Target%20JVM-17-green.svg)](https://jdk.java.net/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.3-green.svg)](https://gradle.org)

[![Build](https://github.com/kpavlov/koog-sauce/actions/workflows/build.yml/badge.svg)](https://github.com/kpavlov/koog-sauce/actions/workflows/build.yml?branch=main)
[![Documentation](https://img.shields.io/badge/Documentation-KDoc-blue)](https://kpavlov.github.io/koog-sauce/)

![logo-256x256.png](docs/logo-256x256.png)

**Koog-sauce** is a missing ingredient that connects [Koog](https://github.com/koog-ai/koog) with other frameworks.

## Features

- **Spring AI Integration** - Provides a Koog's `SpringAiLLMClient`, which uses Spring AI's [ChatClient](https://docs.spring.io/spring-ai/reference/api/chatclient.html) under the hood.
- **LangChain4j Integration** - Offers `Langchain4jLLMClient` for seamless integration with [LangChain4j](https://github.com/langchain4j/langchain4j), supporting both standard and streaming interactions.
- **AI Agent Builder** - Simplifies creation and configuration of AI agents with a fluent builder pattern, making it easy to set up complex agent behaviors.

## Requirements

- JDK 17 or higher
- Gradle 8.14.1 or higher

## Getting Started

### Add Dependency

Add the dependency to your build.gradle.kts file:

```kotlin
dependencies {
    // Core library
    implementation("me.kpavlov:koog-sauce:[LATEST]")

    // For Spring AI integration
    implementation("me.kpavlov:koog-sauce-spring-ai:[LATEST]")
    implementation("org.springframework.ai:spring-ai-openai:1.0.0")

    // For LangChain4j integration
    implementation("me.kpavlov:koog-sauce-langchain4j:[LATEST]")
    implementation("dev.langchain4j:langchain4j:0.24.0")
    implementation("dev.langchain4j:langchain4j-open-ai:0.24.0")

    // Koog library
    implementation("ai.koog:koog:0.3.0")
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

### Spring AI Integration

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

See the [complete example](examples/src/main/kotlin/me/kpavlov/koog/sauce/examples/SpringAiExample.kt).

### LangChain4j Integration

```kotlin
// Create LangChain4j ChatModel
val chatModel = dev.langchain4j.model.openai.OpenAiChatModel.builder()
    .apiKey("your-api-key")
    .modelName("gpt-4")
    .build()

// Create Langchain4jLLMClient
val llmClient = me.kpavlov.koog.sauce.langchain4j.Langchain4jLLMClient(chatModel = chatModel)

// Build a prompt using Koog DSL
val prompt = ai.koog.prompt.dsl.Prompt.build("myPrompt") {
    system("You are a helpful assistant")
    user("Tell me about LangChain4j")
}

// Define the model to use
val model = ai.koog.prompt.llm.LLModel(
    ai.koog.prompt.llm.LLMProvider.OpenAI, 
    "gpt-4", 
    listOf(ai.koog.prompt.llm.LLMCapability.Completion)
)

// Execute the prompt
suspend fun executePrompt() {
    val responses = llmClient.execute(prompt, model)
    println("Response: ${responses.first().content}")
}
```

See the [complete example](examples/src/main/kotlin/me/kpavlov/koog/sauce/examples/LangChain4jAIAgentExample.kt).   


### AI Agent Builder

```kotlin
// Create a prompt executor
val promptExecutor = ai.koog.prompt.executor.model.PromptExecutor(
    llmClient = llmClient,
    defaultModel = model
)

// Create an AI agent using the builder
val agent = me.kpavlov.koog.sauce.agents.core.agent.AIAgent<String, String> {
    this.promptExecutor = promptExecutor
    this.strategy = YourCustomStrategy() // Implement AIAgentStrategy
    this.agentConfig = YourAgentConfig() // Implement AIAgentConfigBase
    this.toolRegistry = ToolRegistry.builder()
        .registerTool(YourCustomTool()) // Add your tools
        .build()
}

// Use the agent
suspend fun executeAgent() {
    val result = agent.execute("Tell me about Kotlin")
    println("Agent result: $result")
}
```

These examples demonstrate how to:
1. Integrate with Spring AI and LangChain4j
2. Build AI agents with the fluent builder pattern
3. Configure different components like prompt executors, strategies, and tools

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

### Integration Tests

The project includes comprehensive tests for both Spring AI and LangChain4j integrations:

- **Spring AI Tests**: `SpringOpenAiTest` demonstrates how to use and test the Spring AI integration with OpenAI models.
- **LangChain4j Tests**: 
  - `Langchain4jLLMClientTest` tests the standard LangChain4j LLM client functionality.
  - `StreamingLangchain4jLLMClientTest` tests the streaming capabilities of the LangChain4j LLM client.

These tests serve as additional examples of how to use the integrations in your own projects.

### Generating Documentation

```bash
./gradlew dokkaGeneratePublicationHtml
```

Or using the Makefile:

```bash
make doc
```

The documentation is automatically generated and published to GitHub Pages when changes are pushed to the main branch. You can access the latest documentation at:

https://kpavlov.github.io/koog-sauce/

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

[![Buy me a Coffee](https://cdn.buymeacoffee.com/buttons/default-orange.png)](https://buymeacoffee.com/mailsk)
