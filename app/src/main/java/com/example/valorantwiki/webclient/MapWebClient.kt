package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.webClientModel.AllMapsResponse
import com.example.valorantwiki.webclient.webClientModel.MapResponse

class MapWebClient {
    private val mapService = RetrofitInitializer().mapService

    suspend fun getAll(language: String): AllMapsResponse = mapService.getAll(language)

    suspend fun getById(id: String, language: String): MapResponse =
        mapService.getById(id, language)
}
