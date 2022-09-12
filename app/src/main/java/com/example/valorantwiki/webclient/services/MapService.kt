package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.AllMapsResponse
import com.example.valorantwiki.webclient.webClientModel.MapResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {

    @GET("maps?language=pt-BR")
    suspend fun getAll() : AllMapsResponse

    @GET("maps/{uuid}")
    suspend fun getById(
        @Path("uuid") uuid: String,
        @Query("language") language: String
    ) : MapResponse
}