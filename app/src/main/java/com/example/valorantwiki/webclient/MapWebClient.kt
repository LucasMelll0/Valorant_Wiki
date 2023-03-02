package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.webClientModel.MapResponseClass

class MapWebClient {

    private val mapService = RetrofitInitializerMaps().mapService

    suspend fun getAll(language: String): List<MapResponseClass> = mapService.getAll(language)

    suspend fun getById(id: String, language: String): MapResponseClass =
        mapService.getById(id, language)
}
