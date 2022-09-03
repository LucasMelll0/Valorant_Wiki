package com.example.valorantwiki.repository

import com.example.valorantwiki.model.Map
import com.example.valorantwiki.webclient.MapWebClient

class MapRepository(
    private val mapWebClient: MapWebClient
) {
   suspend fun getAll() : List<Map> = mapWebClient.getAll() ?: emptyList()
}