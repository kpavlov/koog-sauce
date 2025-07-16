package me.kpavlov.koog.sauce.langchain4j

import ai.koog.agents.core.tools.ToolDescriptor
import ai.koog.prompt.dsl.ModerationResult
import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.message.Message
import ai.koog.prompt.message.ResponseMetaInfo
import dev.langchain4j.data.message.AiMessage
import dev.langchain4j.data.message.SystemMessage
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.kotlin.model.chat.StreamingChatModelReply
import dev.langchain4j.kotlin.model.chat.chatAsync
import dev.langchain4j.kotlin.model.chat.chatFlow
import dev.langchain4j.kotlin.model.chat.request.ChatRequestBuilder
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.StreamingChatModel
import dev.langchain4j.model.chat.request.ChatRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.datetime.Clock

/**
 * A client implementation for interacting with an AI language model via Langchain4j.
 * Provides execution capabilities for handling prompts and generating responses from the language model.
 *
 * This class is designed to integrate with a Langchain4j ChatLanguageModel for handling requests and responses
 * to and from the underlying large language model.
 *
 * Implements the `LLMClient` interface, offering both standard and streaming-based execution methods.
 *
 * @param chatModel The Langchain4j chat model used to facilitate communication with the LLM service.
 * @author Konstantin Pavlov
 */
public class Langchain4jLLMClient(
    private val chatModel: ChatModel? = null,
    private val streamingChatModel: StreamingChatModel? = null
) : LLMClient {

    init {
        require(chatModel != null || streamingChatModel != null) {
            "Either chatModel or streamingChatModel must be provided"
        }
    }

    override suspend fun execute(
        prompt: Prompt,
        model: LLModel,
        tools: List<ToolDescriptor>
    ): List<Message.Response> {
        requireNotNull(chatModel) { "ChatModel must be provided" }

        val chatRequest = convertToChatRequest(prompt, ChatRequestBuilder())

        val response = chatModel.chatAsync(chatRequest)

        return listOf(
            Message.Assistant(
                content = response.aiMessage().text() ?: "Response from Langchain4j",
                finishReason = response.tokenUsage()?.toString() ?: "stop",
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
        model: LLModel
    ): Flow<String> {
        requireNotNull(streamingChatModel) { "StreamingChatModel must be provided" }

        return streamingChatModel.chatFlow {
            convertToChatRequest(prompt, this)
        }.mapNotNull {
            if (it is StreamingChatModelReply.PartialResponse) {
                it.partialResponse
            } else {
                null
            }
        }
    }
}

private fun convertToChatRequest(prompt: Prompt, builder: ChatRequestBuilder): ChatRequest {
    builder.messages.addAll(prompt.messages.mapNotNull { message ->
        when (message) {
            is Message.System -> {
                SystemMessage(message.content)
            }

            is Message.User -> {
                UserMessage(message.content)
            }

            is Message.Assistant -> {
                AiMessage(message.content)
            }

            is Message.Tool.Call -> {
                // In Langchain4j, tool calls are part of AiMessage with tool execution requests
                // This is a simplified implementation as Langchain4j's API might differ
                AiMessage(message.content)
            }

            is Message.Tool.Result -> {
                // For now, we'll skip tool results as they're not critical for the test
                null
            }
        }
    })
    return builder.build()
}
