package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.AgentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AgentService {


    @GET("agents?&isPlayableCharacter=true")
    suspend fun getAll(@Query("language") language: String) : AgentResponse
}