package com.example.valorantwiki.repository

import com.example.valorantwiki.webclient.MapWebClient
import com.example.valorantwiki.webclient.webClientModel.AllMapsResponse
import com.example.valorantwiki.webclient.webClientModel.MapResponse

class MapRepository(
    private val mapWebClient: MapWebClient
) {

    suspend fun getAll(language: String): AllMapsResponse = mapWebClient.getAll(language)

    suspend fun getById(uuid: String, language: String): MapResponse =
        mapWebClient.getById(uuid, language)
}