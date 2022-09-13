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

    private var agentsList: List<Agent>? = null

    suspend fun getAll(): List<Agent>? {
        agentsList?.let {
            return it
        } ?: run {
            Log.i(TAG, "getAll: Repository")
            webClient.getAll(language)?.let {
                agentsList = it
                return agentsList
            }
            return null
        }
    }

    suspend fun getById(uuid: String): Agent? = webClient.getById(uuid, language)

    fun getInitiators(): List<Agent> {
        agentsList?.let { agents ->
            return agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.iniciador)
                    .uppercase()
            }
        } ?: return emptyList()
    }

    fun getControllers(): List<Agent> {
        agentsList?.let { agents ->
            return agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.controlador)
                    .uppercase()
            }
        } ?: return emptyList()
    }

    fun getSentinels(): List<Agent> {
        agentsList?.let { agents ->
            return agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.sentinela)
                    .uppercase()
            }
        } ?: return emptyList()
    }

    fun getDuelists(): List<Agent> {
        agentsList?.let { agents ->
            return agents.filter { agent ->
                agent.role.displayName?.uppercase() == context.getString(R.string.duelista)
                    .uppercase()
            }
        } ?: return emptyList()
    }


}