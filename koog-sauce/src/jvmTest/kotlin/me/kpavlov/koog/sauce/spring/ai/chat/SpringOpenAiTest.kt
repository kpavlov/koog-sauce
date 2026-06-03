package me.kpavlov.koog.sauce.spring.ai.chat

import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ai.koog.prompt.streaming.StreamFrame
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.test.runTest
import dev.mokksy.aimocks.openai.MockOpenai
import dev.mokksy.aimocks.openai.completions.OpenaiChatCompletionRequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.api.OpenAiApi
import kotlin.test.Test

private val mockOpenai = MockOpenai(verbose = true)

internal class SpringOpenAiTest {

    private val chatClient = ChatClient.builder(
        OpenAiChatModel
            .builder()
            .openAiApi(
                OpenAiApi
                    .builder()
                    .apiKey("demo-key")
                    .baseUrl("http://127.0.0.1:${mockOpenai.port()}")
                    .build(),
            ).build(),
    ).build()

    private lateinit var prompt: Prompt

    private val subject = SpringAiLLMClient(chatClient)

    @BeforeEach
    fun setUp() {
        prompt = Prompt.build("clientRequest") {
            system("You are an angry pirate.")
            user("Just say 'Ahoy!'")
        }
    }

    @Test
    fun `Should execute completion request`() = runTest {
        // Given
        mockOpenai.completion {
            requestMatched("gpt-4.1-mini")
        } responds {
            assistantContent = "Ahoy there from sunny Nassau! Hello!"
            finishReason = "stop"
        }

        val model = LLModel(
            provider = LLMProvider.OpenAI,
            id = "gpt-4.1-mini",
            capabilities = listOf(
                LLMCapability.Completion,
            ),
            contextLength = 1000,
        )
        // when
        val responses = subject.execute(prompt, model)

        responses.first() shouldNotBeNull {
            content shouldBe "Ahoy there from sunny Nassau! Hello!"
        }
    }

    @Test
    fun `Should execute stream completion request`() = runTest {
        // Given
        mockOpenai.completion {
            requestMatched("gpt-4.1-nano")
        } respondsStream {
            responseFlow = "Ahoy there from sunny Nassau! Hello!"
                .split(" ")
                .asFlow()
                .transform {
                    delay(42.milliseconds)
                    emit(it)
                }
        }

        val model = LLModel(
            provider = LLMProvider.OpenAI,
            id = "gpt-4.1-nano",
            capabilities = listOf(
                LLMCapability.Completion,
            ),
            contextLength = 1000,
        )

        // when
        val responseFlow = subject.executeStreaming(prompt, model)

        val resultList = mutableListOf<StreamFrame>()
        responseFlow.toList(resultList)
        resultList.filterIsInstance<StreamFrame.TextDelta>()
            .map { it.text }
            .joinToString(separator = " ") shouldBe "Ahoy there from sunny Nassau! Hello!"
    }

    private fun OpenaiChatCompletionRequestSpecification.requestMatched(modelName: String) {
        model(modelName)
    }
}
