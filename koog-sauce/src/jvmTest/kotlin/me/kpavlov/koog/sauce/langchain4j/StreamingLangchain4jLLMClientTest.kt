package me.kpavlov.koog.sauce.langchain4j

import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.AiMessage.aiMessage
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler
import dev.langchain4j.model.output.TokenUsage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

internal class StreamingLangchain4jLLMClientTest : AbstractLangchain4jLLMClientTest() {

    private lateinit var mockChatModel: ChatModel
    private lateinit var mockStreamingChatModel: StreamingChatModel

    @BeforeEach
    fun setUp() {
        mockChatModel = mockk()
        mockStreamingChatModel = mockk()
        subject = Langchain4jLLMClient(
            chatModel = null,
            streamingChatModel = mockStreamingChatModel
        )
    }

    @Test
    fun `Should NOT execute completion request`() = runTest {
        shouldThrow<IllegalArgumentException> { subject.execute(prompt, model) }
    }

    @Test
    fun `Should execute stream completion request`() = runTest {
        // Given
        val model = LLModel(
            LLMProvider.OpenAI, "gpt-4.1-nano", listOf(
                LLMCapability.Completion,
            )
        )

        val responseText = "Response from Langchain4j"
        val completeResponse =
            ChatResponse
                .builder()
                .aiMessage(aiMessage(responseText))
                .build()

        every {
            mockStreamingChatModel.chat(
                any<ChatRequest>(),
                any<StreamingChatResponseHandler>()
            )
        } answers {
            val handler = it.invocation.args[1] as StreamingChatResponseHandler
            responseText.split(" ")
                .forEach { token -> handler.onPartialResponse("$token ") }
            handler.onCompleteResponse(completeResponse)
        }

        // when
        val flow = subject.executeStreaming(prompt, model)

        val resultList = flow.toList()

        resultList.joinToString(separator = "").removeSuffix(" ") shouldBe responseText
    }
}
