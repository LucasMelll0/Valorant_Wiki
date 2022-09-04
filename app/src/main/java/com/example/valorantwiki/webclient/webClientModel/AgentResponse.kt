package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Agent

data class AgentResponse(
    val status: Int,
    val data: List<AgentResponseClass>
)

data class AgentResponseClass(
    private val uuid: String,
    private val displayName: String?,
    private val description: String?,
    private val fullPortrait: String?,
    private val backgroundGradientColors: List<String>?,
    private val role: Role?,
    private val abilities: List<Abilitie>?

) {
    val agent: Agent
        get() = Agent(
            uuid = this.uuid,
            name = this.displayName ?: "",
            description = this.description ?: "",
            image = this.fullPortrait ?: "",
            backgrondColor = this.backgroundGradientColors?.get(2) ?: "",
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

data class Abilitie(
    val slot: String?,
    val displayName: String?,
    val description: String?,
    val displayIcon: String?
)