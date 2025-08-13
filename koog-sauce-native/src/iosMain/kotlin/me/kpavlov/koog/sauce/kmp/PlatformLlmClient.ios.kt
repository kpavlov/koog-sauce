package me.kpavlov.koog.sauce.kmp

import kotlinx.coroutines.flow.Flow

actual class PlatformLlmClient {
    actual fun isSupported(): Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun chat(
        systemPrompt: String,
        userPrompt: String,
    ): String {
        TODO("Not yet implemented")
    }

    actual fun chatFlow(systemPrompt: String, userPrompt: String): Flow<String> {
        TODO("Not yet implemented")
    }
}
