package com.example.valorantwiki.viewmodel.agentlistviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantwiki.repository.AgentRepository

@Suppress("UNCHECKED_CAST")
class AgentListViewModelFactory(
    private val application: Application,
    private val repository: AgentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AgentListViewModel::class.java)) {
            AgentListViewModel(this.application, this.repository) as T
        } else {
            throw java.lang.IllegalArgumentException("AgentListViewModel Not Found")
        }
    }
}