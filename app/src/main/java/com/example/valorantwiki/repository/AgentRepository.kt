package com.example.valorantwiki.repository

import android.util.Log
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.AgentWebClient
import com.example.valorantwiki.webclient.SUCCESS_CODE
import com.example.valorantwiki.webclient.TAG
import com.example.valorantwiki.webclient.webClientModel.AgentResponse

class AgentRepository(
    private val webClient: AgentWebClient
) {

    suspend fun getAll(language: String): List<Agent>? {
        return try {
            val response = webClient.getAll(language)
            return if (response.status == SUCCESS_CODE) {
                val agents = response.data.map { it.agent }
                val agentsSorted = agents.sortedBy { it.name }
                agentsSorted
            }else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: ", e)
            null
        }
    }

    suspend fun getById(uuid: String, language: String): AgentResponse =
        webClient.getById(uuid, language)
}
