package com.example.valorantwiki.repository

import android.content.Context
import android.util.Log
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.AgentWebClient
import com.example.valorantwiki.webclient.TAG

class AgentRepository(
    private val context: Context,
    private val webClient: AgentWebClient
) {
    private val language = context.getString(R.string.linguagem)

    companion object {
        private var agentsList: List<Agent>? = null
    }

    suspend fun getAll(): List<Agent>? {
        return agentsList ?: run {
            Log.i(TAG, "getAll: Repository")
            webClient.getAll(language)?.let {
                agentsList = it
                agentsList
            }
        }
    }

    suspend fun getById(uuid: String): Agent? = webClient.getById(uuid, language)

    fun getInitiators(): List<Agent> {
        return agentsList?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.iniciador)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getControllers(): List<Agent> {
        return agentsList?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.controlador)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getSentinels(): List<Agent> {
        return agentsList?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.sentinela)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getDuelists(): List<Agent> {
        return agentsList?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.duelista)
                    .uppercase()
            }
        } ?: emptyList()
    }


}