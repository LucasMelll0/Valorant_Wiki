package com.example.valorantwiki.repository

import android.util.Log
import com.example.valorantwiki.webclient.MapWebClient
import com.example.valorantwiki.webclient.webClientModel.MapResponseClass

class MapRepository(
    private val mapWebClient: MapWebClient
) {
    companion object {
        const val TAG = "MapRepository"
    }

    suspend fun getAll(language: String): List<MapResponseClass> {
        return try {
            mapWebClient.getAll(language).sortedBy { it.map.name }
        }catch (e: Exception) {
            Log.w(TAG, "getAll: ", e)
            emptyList()
        }
    }

    suspend fun getById(uuid: String, language: String): MapResponseClass =
        mapWebClient.getById(uuid, language)
}