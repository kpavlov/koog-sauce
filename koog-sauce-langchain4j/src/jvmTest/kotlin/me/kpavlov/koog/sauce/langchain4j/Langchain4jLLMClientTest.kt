package me.kpavlov.koog.sauce.langchain4j

import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.response.ChatResponse
import dev.langchain4j.model.output.TokenUsage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

internal class Langchain4jLLMClientTest : AbstractLangchain4jLLMClientTest() {

    private lateinit var mockChatModel: ChatModel

    @BeforeEach
    fun setUp() {
        mockChatModel = mockk()
        subject = Langchain4jLLMClient(
            chatModel = mockChatModel,
            streamingChatModel = null
        )
    }

    @Test
    fun `Should execute completion request`() = runTest {
        // Given
        val mockAiMessage = mockk<AiMessage>()
        val mockChatResponse = mockk<ChatResponse>()
        val mockTokenUsage = mockk<TokenUsage>()

        every { mockAiMessage.text() } returns "Ahoy there from sunny Nassau! Hello!"
        every { mockChatResponse.aiMessage() } returns mockAiMessage
        every { mockChatResponse.tokenUsage() } returns mockTokenUsage
        every { mockTokenUsage.toString() } returns "stop"

        every { mockChatModel.chat(any<ChatRequest>()) } returns mockChatResponse

        // when
        val responses = subject.execute(prompt, model)

        responses.first() shouldNotBeNull {
            content shouldBe "Ahoy there from sunny Nassau! Hello!"
        }
    }

    @Test
    fun `Should NOT execute streaming request`() = runTest {
        shouldThrow<IllegalArgumentException> { subject.executeStreaming(prompt, model) }
    }
}
