package me.kpavlov.koog.sauce.examples

import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.agents.core.agent.singleRunStrategy
import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import kotlinx.coroutines.runBlocking
import me.kpavlov.finchly.TestEnvironment
import me.kpavlov.koog.sauce.agents.core.agent.AIAgent
import me.kpavlov.koog.sauce.spring.ai.chat.SpringAiLLMClient
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.api.OpenAiApi

/**
 * Example of using Spring AI integration with koog-sauce and loading environment variables from .env file.
 *
 * Before running this example:
 * 1. Create a .env file in the project root directory with your OpenAI API key:
 *    OPENAI_API_KEY=your-api-key-here
 *    OPENAI_MODEL=gpt-4.1-nano
 * 2. Run the example
 */
fun main() {
    // Get API key and model from environment variables
    val apiKey = TestEnvironment["OPENAI_API_KEY"]
        ?: throw IllegalStateException("OPENAI_API_KEY not found in .env file")
    val modelName = TestEnvironment.get("OPENAI_MODEL", "gpt-4.1-nano")!!

    println("Using OpenAI model: $modelName")

    // Create Spring AI ChatClient
    val chatClient = ChatClient.builder(
        OpenAiChatModel
            .builder()
            .openAiApi(
                OpenAiApi
                    .builder()
                    .apiKey(apiKey)
                    .build()
            )
            .build()
    ).build()

    // Create Spring AI LLM client
    val llmClient = SpringAiLLMClient(chatClient)

    // Define the model to use with koog
    val model = LLModel(
        provider = LLMProvider.OpenAI,
        id = modelName,
        capabilities = listOf(LLMCapability.Completion)
    )

    // Use the LLM client
    runBlocking {
        // Build a prompt using Koog DSL
        val prompt = Prompt.build("simple-prompt") {
            system("You are a helpful assistant that provides concise and accurate information.")
            user("Tell me about Kotlin Multiplatform in 3 sentences.")
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
        val response = agent.run("Tell me about Kotlin Multiplatform in 3 sentences.")


        // Print the response
        println("Response: $response")
    }
}
