package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.AgentResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

interface AgentService {

    @GET("agents?language=pt-BR&isPlayableCharacter=true")
    suspend fun getAll() : AgentResponse
}