package com.example.valorantwiki.webclient

import android.util.Log
import com.example.valorantwiki.model.Agent

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
}