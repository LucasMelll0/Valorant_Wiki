@file:Suppress("UNCHECKED_CAST")

package com.example.valorantwiki.viewmodel.agentviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantwiki.repository.AgentRepository

class AgentViewModelFactory(
    private val application: Application,
    private val repository: AgentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AgentViewModel::class.java)) {
            AgentViewModel(application, repository) as T
        } else {
            throw java.lang.IllegalArgumentException("AgentViewModel Not Found")
        }
    }
}