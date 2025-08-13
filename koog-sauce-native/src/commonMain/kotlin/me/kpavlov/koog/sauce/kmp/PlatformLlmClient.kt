package me.kpavlov.koog.sauce.kmp

import kotlinx.coroutines.flow.Flow

expect class PlatformLlmClient {

    /**
     * Returns true if the Native LLM is supported on the current platform.
     */
    fun isSupported(): Boolean

    suspend fun chat(systemPrompt: String, userPrompt: String): String

    fun chatFlow(systemPrompt: String, userPrompt: String): Flow<String>
}
