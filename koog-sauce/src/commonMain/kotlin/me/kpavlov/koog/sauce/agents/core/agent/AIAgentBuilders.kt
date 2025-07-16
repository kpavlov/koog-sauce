package me.kpavlov.koog.sauce.agents.core.agent

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.agent.AIAgent.FeatureContext
import ai.koog.agents.core.agent.config.AIAgentConfigBase
import ai.koog.agents.core.agent.entity.AIAgentStrategy
import ai.koog.agents.core.agent.singleRunStrategy
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.prompt.executor.model.PromptExecutor
import kotlinx.datetime.Clock

public class AiAgentBuilder(
    public var promptExecutor: PromptExecutor? = null,
    public var strategy: AIAgentStrategy = singleRunStrategy(),
    public var agentConfig: AIAgentConfigBase? = null,
    public var toolRegistry: ToolRegistry = ToolRegistry.EMPTY,
    public var clock: Clock = Clock.System,
    public var installFeatures: FeatureContext.() -> Unit = {}
) {
    internal fun build(): AIAgent = AIAgent(
        promptExecutor = requireNotNull(promptExecutor) { "PromptExecutor must be provided" },
        strategy = strategy,
        agentConfig = requireNotNull(agentConfig) { "AgentConfig must be provided" },
        toolRegistry = toolRegistry,
        clock = clock,
        installFeatures = installFeatures,
    )
}

public fun AIAgent(block: AiAgentBuilder.() -> Unit): AIAgent {
    val builder = AiAgentBuilder()
    builder.apply(block)
    return builder.build()
}
