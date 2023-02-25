package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.webClientModel.AgentResponse
import com.example.valorantwiki.webclient.webClientModel.AllAgentsResponse

class AgentWebClient {

    private val agentService = RetrofitInitializer().agentService

    suspend fun getAll(language: String): AllAgentsResponse = agentService.getAll(language)

    suspend fun getById(id: String, language: String): AgentResponse =
        agentService.getById(id, language)
}