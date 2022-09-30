package com.example.valorantwiki.repository

import android.content.Context
import com.example.valorantwiki.R
import com.example.valorantwiki.model.Map
import com.example.valorantwiki.webclient.MapWebClient

class MapRepository(
    context: Context,
    private val mapWebClient: MapWebClient
) {
    private val language = context.getString(R.string.linguagem)
    private var mapsList: List<Map>? = null

    suspend fun getAll(): List<Map>? {
        return mapsList ?: kotlin.run {
            mapWebClient.getAll()?.let {
                mapsList = it
                mapsList
            }

        }
    }

    suspend fun getById(uuid: String): Map? = mapWebClient.getById(uuid, language)
}