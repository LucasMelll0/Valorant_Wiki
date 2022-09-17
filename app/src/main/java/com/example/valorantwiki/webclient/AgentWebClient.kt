package com.example.valorantwiki.webclient

import android.util.Log
import com.example.valorantwiki.model.Agent

class AgentWebClient {

    private val agentService = RetrofitInicializer().agentService

    suspend fun getAll(language: String): List<Agent>? {
        return try {
            val agentsResponse = agentService.getAll(language)

            return if (agentsResponse.status == 200) {
                agentsResponse.data.map { agent ->
                    agent.agent
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getById(uuid: String, language: String): Agent? {
        return try {
            val agentResponse = agentService.getById(uuid, language)

            agentResponse.data.agent
        } catch (e: Exception) {
            Log.e(TAG, "getById: ", e)
            null
        }
    }
}