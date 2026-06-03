package me.kpavlov.koog.sauce.spring.ai.chat

import ai.koog.agents.core.tools.ToolDescriptor
import ai.koog.prompt.dsl.ModerationResult
import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.message.Message
import ai.koog.prompt.message.ResponseMetaInfo
import ai.koog.prompt.streaming.StreamFrame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlin.time.Clock
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.ToolResponseMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.ChatOptions

/**
 * A client implementation for interacting with an AI language model via a chat-based API.
 * Provides execution capabilities for handling prompts and generating responses from the language model.
 *
 * This class is designed to integrate with a `ChatClient` for handling requests and responses
 * to and from the underlying large language model.
 *
 * Implements the `LLMClient` abstract class, offering both standard and streaming-based execution methods.
 *
 * @param chatClient The chat client used to facilitate communication with the LLM service.
 * @author Konstantin Pavlov
 */
public class SpringAiLLMClient(
    private val chatClient: ChatClient,
) : LLMClient() {

    override fun llmProvider(): LLMProvider = SpringAiProvider

    override fun close() {}

    override suspend fun execute(
        prompt: Prompt,
        model: LLModel,
        tools: List<ToolDescriptor>
    ): List<Message.Response> {
        val chatResponse = requireNotNull(
            prepareClientRequest(prompt, model)
                .call()
                .chatResponse()
        ) { "Chat response must not be null" }
        val result = chatResponse.result
        return listOf(
            Message.Assistant(
                content = result.output.text!!,
                finishReason = result.metadata.finishReason,
                metaInfo = ResponseMetaInfo(
                    timestamp = Clock.System.now(),
                )
            )
        )
    }

    override suspend fun moderate(
        prompt: Prompt,
        model: LLModel
    ): ModerationResult {
        TODO("Not yet implemented")
    }

    override fun executeStreaming(
        prompt: Prompt,
        model: LLModel,
        tools: List<ToolDescriptor>
    ): Flow<StreamFrame> {
        return prepareClientRequest(prompt, model)
            .stream()
            .chatResponse()
            .asFlow()
            .map { it.result?.output?.text }
            .filterNotNull()
            .filter { it.isNotEmpty() }
            .map { StreamFrame.TextDelta(it) }
    }

    private fun prepareClientRequest(
        prompt: Prompt,
        model: LLModel,
    ): ChatClient.ChatClientRequestSpec {
        val springAiMessages = prompt.messages.map {
            when (it) {
                is Message.System -> SystemMessage(it.content)

                is Message.User -> UserMessage(it.content)

                is Message.Assistant -> AssistantMessage(it.content)

                is Message.Tool.Call -> AssistantMessage.builder()
                    .toolCalls(
                        listOf(
                            AssistantMessage.ToolCall(
                                it.id ?: "",
                                "function",
                                it.tool,
                                it.content
                            )
                        )
                    )
                    .build()

                is Message.Tool.Result -> ToolResponseMessage.builder()
                    .responses(
                        listOf(
                            ToolResponseMessage.ToolResponse(
                                it.id ?: "",
                                it.tool,
                                it.content
                            )
                        )
                    )
                    .build()

                is Message.Reasoning -> AssistantMessage.builder()
                    .content("")
                    .build()
            }
        }

        return chatClient
            .prompt()
            .messages(springAiMessages)
            .options(
                ChatOptions
                    .builder()
                    .model(model.id)
                    .build(),
            )
    }

    private companion object {
        private object SpringAiProvider : LLMProvider("spring-ai", "Spring AI")
    }
}
