package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.MapResponse
import retrofit2.http.GET

interface MapService {

    @GET("maps?language=pt-BR")
    suspend fun getAll() : MapResponse
}