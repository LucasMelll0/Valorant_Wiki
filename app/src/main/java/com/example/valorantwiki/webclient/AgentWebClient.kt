package com.example.valorantwiki.webclient

import android.util.Log
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.webClientModel.AgentResponse

class AgentWebClient {

    private val agentService = RetrofitInicializer().agentService

    suspend fun getAll(language: String): List<Agent>?{
        return try {
            val agentsReponse = agentService.getAll(language)

            agentsReponse.data.map { agent ->
                agent.agent
            }
        }catch (e: Exception){
            Log.e(TAG, "buscaTodos: ", e)
            null
        }
    }

    suspend fun getById(uuid: String, language: String) : Agent? {
        return try {
            val agentResponse = agentService.getById(uuid, language)

            agentResponse.data.agent
        }catch (e: Exception){
            Log.e(TAG, "getById: ", e)
            null
        }
    }
}