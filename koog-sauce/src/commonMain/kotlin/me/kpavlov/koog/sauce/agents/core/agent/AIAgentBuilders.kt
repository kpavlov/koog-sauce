package me.kpavlov.koog.sauce.agents.core.agent

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.agent.AIAgent.FeatureContext
import ai.koog.agents.core.agent.config.AIAgentConfigBase
import ai.koog.agents.core.agent.entity.AIAgentStrategy
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.prompt.executor.model.PromptExecutor
import kotlinx.datetime.Clock

public class AiAgentBuilder<I, O>(
    public var promptExecutor: PromptExecutor? = null,
    public var strategy: AIAgentStrategy<I, O>? = null,
    public var agentConfig: AIAgentConfigBase? = null,
    public var toolRegistry: ToolRegistry = ToolRegistry.EMPTY,
    public var clock: Clock = Clock.System,
    public var installFeatures: FeatureContext.() -> Unit = {}
) {
    public inline fun <reified Input, reified Output> build(): AIAgent<Input, Output> = AIAgent<Input, Output>(
        promptExecutor = requireNotNull(promptExecutor) { "PromptExecutor must be provided" },
        strategy = requireNotNull(strategy) { "Strategy must be provided" } as AIAgentStrategy<Input, Output>,
        agentConfig = requireNotNull(agentConfig) { "AgentConfig must be provided" },
        toolRegistry = toolRegistry,
        clock = clock,
        installFeatures = installFeatures,
    )
}

public inline fun <reified Input, reified Output> AIAgent(block: AiAgentBuilder<Input, Output>.() -> Unit): AIAgent<Input, Output> {
    val builder = AiAgentBuilder<Input, Output>()
    builder.apply(block)
    return builder.build()
}
