package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.AgentResponse
import com.example.valorantwiki.webclient.webClientModel.allAgentsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AgentService {


    @GET("agents?&isPlayableCharacter=true")
    suspend fun getAll(@Query("language") language: String) : allAgentsResponse

    @GET("agents/{uuid}?")
    suspend fun getById(@Path("uuid") uuid: String, @Query("language") language: String) : AgentResponse
}