package com.example.valorantwiki.viewmodel.agentviewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.repository.AgentRepository

class AgentViewModel(
    application: Application,
    private val repository: AgentRepository
) :
    ViewModel() {

    private val language = application.getString(R.string.linguagem)
    internal val agentLiveData = MutableLiveData<Agent>()

    suspend fun getById(id: String){
        agentLiveData.value ?: run {
            val response = repository.getById(id, language)
            if (response.status == 200){
                val agent = response.data.agent
                agentLiveData.postValue(agent)
            }
        }
    }
}