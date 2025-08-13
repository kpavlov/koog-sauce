package me.kpavlov.koog.sauce.kmp

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import io.ktor.client.HttpClientConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.seconds

internal class OpenAILLMClient(
    apiKey: String,
    httpClientConfig: HttpClientConfig<*>.() -> Unit = {}
) {

    private val openAI = OpenAI(
        token = apiKey,
        timeout = Timeout(socket = 60.seconds),
        httpClientConfig = httpClientConfig
    )

    suspend fun chat(
        modelName: String = "gpt-4.1-nano",
        systemPrompt: String,
        userPrompt: String,
    ): Result<String> {
        return runCatching {
            val chatCompletionRequest = createRequest(modelName, systemPrompt, userPrompt)
            val chatCompletion = openAI.chatCompletion(chatCompletionRequest)
            chatCompletion.choices.firstOrNull()?.message?.content ?: "Sorry, can't help you right now"
        }
    }

    fun chatFlow(
        modelName: String = "gpt-4.1-nano",
        systemPrompt: String,
        userPrompt: String,
    ): Flow<String> {
        val chatCompletionRequest = createRequest(modelName, systemPrompt, userPrompt)

        val completions: Flow<ChatCompletionChunk> =
            openAI.chatCompletions(chatCompletionRequest)

        return completions.map { it.choices.first().delta?.content }.filterNotNull()
    }

    private fun createRequest(
        modelName: String,
        systemPrompt: String,
        userPrompt: String
    ): ChatCompletionRequest = ChatCompletionRequest(
        model = ModelId(modelName),
        messages = listOf(
            ChatMessage(
                role = ChatRole.System,
                content = systemPrompt,
            ),
            ChatMessage(
                role = ChatRole.User,
                content = userPrompt
            )
        ),
        maxCompletionTokens = 300,
    )
}
