package com.example.valorantwiki.repository

import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.AgentWebClient

class AgentRepository(
    private val webClient: AgentWebClient
) {
    suspend fun getAll(language: String): List<Agent> = webClient.getAll(language) ?: emptyList()

}