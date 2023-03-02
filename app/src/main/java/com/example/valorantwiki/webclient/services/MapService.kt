package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.MapResponseClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {

    @GET("maps?")
    suspend fun getAll(
        @Query("language") language: String
    ) : List<MapResponseClass>

    @GET("maps/{uuid}?")
    suspend fun getById(
        @Path("uuid") uuid: String,
        @Query("language") language: String
    ) : MapResponseClass
}