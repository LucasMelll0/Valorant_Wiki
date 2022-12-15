package com.example.valorantwiki.viewmodel.agentlistviewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.repository.AgentRepository

class AgentListViewModel(
    private val application: Application,
    private val repository: AgentRepository
) : ViewModel() {

    private val language = application.getString(R.string.linguagem)

    companion object {
        private val agentsLiveDataCompanion = MutableLiveData<List<Agent>>()
    }

    internal val agentsLiveData = agentsLiveDataCompanion

    suspend fun getAll() {
        agentsLiveData.value ?: run {
            val response = repository.getAll(language)
            if (response.status == 200) {
                val agents = response.data.map { agent -> agent.agent }
                val agentsSorted = agents.sortedBy { it.name }
                agentsLiveData.postValue(agentsSorted)
            }
        }
    }

    fun getForSelectedRole(selectedRole: Int): List<Agent> {
        return agentsLiveData.value?.let {
            when (selectedRole) {
                R.id.page_all -> {
                    agentsLiveData.value
                }
                R.id.page_initiator -> {
                    getInitiators()
                }
                R.id.page_controller -> {
                    getControlers()
                }
                R.id.page_sentinel -> {
                    getSentinels()
                }
                R.id.page_duelist -> {
                    getDuelists()
                }
                else -> {
                    emptyList()
                }
            }
        } ?: emptyList()
    }

    fun getInitiators(): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == application.getString(R.string.iniciador)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getControlers(): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == application.getString(R.string.controlador)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getSentinels(): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == application.getString(R.string.sentinela)
                    .uppercase()
            }
        } ?: emptyList()
    }

    fun getDuelists(): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            agents.filter { agent ->
                agent.role.displayName?.uppercase() == application.getString(R.string.duelista)
                    .uppercase()
            }
        } ?: emptyList()
    }
}