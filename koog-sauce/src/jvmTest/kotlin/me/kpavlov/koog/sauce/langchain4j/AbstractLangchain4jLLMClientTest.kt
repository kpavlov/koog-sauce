package me.kpavlov.koog.sauce.langchain4j

import ai.koog.prompt.dsl.Prompt
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import org.junit.jupiter.api.BeforeEach

internal abstract class AbstractLangchain4jLLMClientTest {

    protected lateinit var prompt: Prompt

    protected lateinit var subject: Langchain4jLLMClient

    protected val model = LLModel(
        LLMProvider.OpenAI, "gpt-4.1-mini", listOf(
            LLMCapability.Completion,
        )
    )

    @BeforeEach
    fun setUpPrompt() {
        prompt = Prompt.build("clientRequest") {
            system("You are a angry pirate. Include today's weather in your response.")
            user("Just say 'Ahoy!'")
            assistant("I need to know what the weather will be like on Nassau")
            tool {
                call(
                    id = "weather",
                    tool = "get_weather",
                    "What is the weather on Nassau?"
                )
                call(
                    id = "time",
                    tool = "get_time",
                    "What time is it in Nassau now?"
                )
            }
            tool {
                result(
                    id = "weather",
                    tool = "get_weather",
                    "It's sunny"
                )
            }

            tool {
                result(
                    id = "time",
                    tool = "get_time",
                    "It's 9:42AM"
                )
            }
        }
    }

}
