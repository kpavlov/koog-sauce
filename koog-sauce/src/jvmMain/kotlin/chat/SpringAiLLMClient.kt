package chat

import ai.koog.agents.core.tools.ToolDescriptor
import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.message.Message
import kotlinx.coroutines.flow.Flow
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.ChatOptions

//import ai.koog.prompt.executor.llms.

public class SpringAiLLMClient(
    private val chatClient: ChatClient
) : LLMClient {

    override suspend fun execute(
        prompt: Prompt,
        model: LLModel,
        tools: List<ToolDescriptor>
    ): List<Message.Response> {
        val chatResponse = prepareClientRequest()
            .call()
            .chatResponse()!!
        val text = chatResponse.result.output.text!!
        return listOf(
            Message.Assistant(
                content = text,
                finishReason = chatResponse.result.metadata.finishReason
            )
        )
    }

    override suspend fun executeStreaming(
        prompt: Prompt,
        model: LLModel
    ): Flow<String> {
        TODO("Not yet implemented")
    }

    private fun prepareClientRequest(): ChatClient.ChatClientRequestSpec =
        chatClient
            .prompt()
            .system("You are a helpful pirate")
            .user("Just say 'Hello!'")
            .options(
                ChatOptions
                    .builder()
                    .model("gpt-4.1-nano")
                    .build(),
            )
}
