package com.example.valorantwiki.repository

import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.AgentWebClient
import kotlinx.coroutines.flow.Flow

class AgentRepository(
    private val webClient: AgentWebClient
) {
    suspend fun buscaTodos(): List<Agent> {
        return webClient.buscaTodos() ?: emptyList()
    }
}