package me.kpavlov.koog.sauce.agents.core.agent.me.kpavlov.koog.sauce.agents.core.agent

import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.prompt.executor.model.PromptExecutor
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.datetime.Clock
import me.kpavlov.koog.sauce.agents.core.agent.AIAgent
import kotlin.test.Test

class AIAgentBuildersTest {

    @Test
    fun `Should build agent`() {
        val executor = mockk<PromptExecutor>()
        val config = mockk<AIAgentConfig>()
        val registry = mockk<ToolRegistry>()
        val theClock = mockk<Clock>()
        // when
        val agent = AIAgent {
            promptExecutor = executor
            agentConfig = config
            toolRegistry = registry
            clock = theClock
        }
        // then
        agent shouldNotBeNull {
            promptExecutor shouldBe executor
            agentConfig shouldBe config
            clock shouldBe theClock
            toolRegistry shouldBe registry
        }
    }

}
