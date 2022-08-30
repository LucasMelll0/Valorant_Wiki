package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Agent

class AgentResponse(
    val status: Int,
    val data: List<AgentResponseClass>
)

class AgentResponseClass(
    val uuid: String,
    val displayName: String?,
    val description: String?,
    val fullPortrait: String?,
    val role: Role?,
    val abilities: List<Abilitie>?

) {
    val agent: Agent
        get() = Agent(
            uuid = this.uuid,
            displayName = this.displayName ?: "",
            description = this.description ?: "",
            fullPortrait = this.fullPortrait ?: "",
            role = this.role ?: Role(
                "",
                ""
            ),
            abilities = this.abilities ?: emptyList()
        )
}

class Role(
    displayName: String?,
    description: String?,
)

class Abilitie(
    slot: String?,
    displayName: String?,
    description: String?,
    displayIcon: String?
)