package com.example.valorantwiki.repository

import android.content.Context
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Agent
import com.example.valorantwiki.webclient.AgentWebClient

class AgentRepository(
    context: Context,
    private val webClient: AgentWebClient
) {
    private val language = context.getString(R.string.linguagem)

    suspend fun getAll(): List<Agent> = webClient.getAll(language) ?: emptyList()

    suspend fun getById(uuid: String): Agent? = webClient.getById(uuid, language)

}