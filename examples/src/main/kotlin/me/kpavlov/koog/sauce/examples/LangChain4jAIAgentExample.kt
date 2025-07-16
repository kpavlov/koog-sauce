package me.kpavlov.koog.sauce.examples

import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.agents.core.agent.singleRunStrategy
import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import dev.langchain4j.model.openai.OpenAiChatModel
import kotlinx.coroutines.runBlocking
import me.kpavlov.finchly.TestEnvironment
import me.kpavlov.koog.sauce.agents.core.agent.AIAgent
import me.kpavlov.koog.sauce.langchain4j.Langchain4jLLMClient

/**
 * Example of using LangChain4j integration with koog-sauce and loading environment variables from .env file.
 *
 * Before running this example:
 * 1. Create a .env file in the project root directory with your OpenAI API key:
 *    OPENAI_API_KEY=your-api-key-here
 *    OPENAI_MODEL=gpt-4.1-nano
 * 2. Run the example
 */
fun main() {
    // Load environment variables from .env file

    // Get API key and model from environment variables
    val apiKey = TestEnvironment["OPENAI_API_KEY"]
        ?: throw IllegalStateException("OPENAI_API_KEY not found in .env file")
    val modelName = TestEnvironment.get("OPENAI_MODEL", "gpt-4.1-nano")

    println("Using OpenAI model: $modelName")

    // Create LangChain4j ChatModel
    val chatModel = OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName(modelName)
        .maxCompletionTokens(50)
        .logResponses(true)
        .logRequests(true)
        .build()

    // Create LangChain4j LLM client
    val llmClient = Langchain4jLLMClient(chatModel = chatModel)

    // Define the model to use with koog
    val model = LLModel(
        provider = LLMProvider.OpenAI,
        id = "openai-model",
        capabilities = listOf(LLMCapability.Completion)
    )

    // Use the LLM client
    val response = runBlocking {
        // Build a prompt using Koog DSL
        val prompt = Prompt.build("simple-prompt") {
            system("You are a helpful assistant that provides concise and accurate information.")
        }

        val agent = AIAgent {
            strategy = singleRunStrategy()
            promptExecutor = SingleLLMPromptExecutor(llmClient)
            agentConfig = AIAgentConfig(
                prompt = prompt,
                model = model,
                maxAgentIterations = 2,
            )
        }
        agent.run("Tell me about Kotlin Multiplatform in 3 sentences.")
    }

    // Print the response
    println("Response: $response")
}
