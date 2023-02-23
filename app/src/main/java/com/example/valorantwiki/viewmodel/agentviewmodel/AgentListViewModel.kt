package com.example.valorantwiki.viewmodel.agentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.repository.AgentRepository

class AgentListViewModel(
    private val repository: AgentRepository
) : ViewModel() {

    companion object {
        private val agentsLiveDataCompanion = MutableLiveData<List<Agent>>(emptyList())
    }

    internal val agentsLiveData: LiveData<List<Agent>> = agentsLiveDataCompanion

    private val agentRole = MutableLiveData<String?>()

    fun getAgentRoleFilter(): String? {
        return agentRole.value
    }

    fun setAgentRoleFilter(role: String?) {
        agentRole.postValue(role)
    }

    suspend fun getAll(language: String) {
        agentsLiveData.value?.let { livedataValue ->
            if (livedataValue.isEmpty()) {
                val agents = repository.getAll(language)
                agents?.let {
                    agentsLiveDataCompanion.postValue(it)
                }
            }
        }
    }

    fun searchByName(query: String): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            agents.filter { agent ->
                agent.name.startsWith(query, true)
            }
        } ?: emptyList()
    }

    fun getByRoleName(roleName: String?): List<Agent> {
        return agentsLiveData.value?.let { agents ->
            roleName?.let {
                if (it.isNotEmpty()){
                    agents.filter { agent ->
                        agent.role.displayName?.uppercase() == roleName
                            .uppercase()
                    }.sortedBy { agent -> agent.name }
                }else{
                    agentsLiveData.value
                }
            } ?: agentsLiveData.value
        } ?: emptyList()
    }
}