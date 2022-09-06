package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Agent

data class AllAgentsResponse(
    val status: Int,
    val data: List<AgentResponseClass>
)
data class AgentResponse(
    val status: Int,
    val data: AgentResponseClass
)

data class AgentResponseClass(
    private val uuid: String,
    private val displayName: String?,
    private val description: String?,
    private val fullPortrait: String?,
    private val backgroundGradientColors: List<String>?,
    private val role: Role?,
    private val abilities: List<Ability>?

) {
    val agent: Agent
        get() = Agent(
            uuid = this.uuid,
            name = this.displayName ?: "",
            description = this.description ?: "",
            image = this.fullPortrait ?: "",
            backgrondColor = this.backgroundGradientColors?.get(1) ?: "",
            role = this.role ?: Role(
                "",
                ""
            ),
            abilities = this.abilities ?: emptyList()
        )
}

data class Role(
    val displayName: String?,
    val description: String?,
)

data class Ability(
    val slot: String?,
    val displayName: String?,
    val description: String?,
    val displayIcon: String?
)