package me.kpavlov.koog.sauce.kmp

import io.kotest.matchers.result.shouldBeSuccess
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test

private const val API_KEY = "sk-proj-..."

class OpenAILLMClientTest {

    @Test
    @Ignore
    fun `test OpenAI LLM client`(): Unit = runTest {

        val client = OpenAILLMClient(apiKey = API_KEY) {
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 3000
                socketTimeoutMillis = 10000
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }

        val result = client.chat(
            modelName = "gpt-4.1-nano",
            systemPrompt = "You are a helpful and concise assistant.",
            userPrompt = "What is the capital of France? Answer with one word only."
        )
        result shouldBeSuccess "Paris"
    }
}
