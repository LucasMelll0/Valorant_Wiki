package com.example.valorantwiki.repository

import com.example.valorantwiki.webclient.AgentWebClient
import com.example.valorantwiki.webclient.webClientModel.AgentResponse
import com.example.valorantwiki.webclient.webClientModel.AllAgentsResponse

class AgentRepository(
    private val webClient: AgentWebClient
) {

    suspend fun getAll(language: String): AllAgentsResponse {
        return webClient.getAll(language)
    }

    suspend fun getById(uuid: String, language: String): AgentResponse =
        webClient.getById(uuid, language)
}
