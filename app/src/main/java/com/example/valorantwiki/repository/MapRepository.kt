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
        mapsList?.let {
            return it
        } ?: kotlin.run {
            mapWebClient.getAll()?.let {
                mapsList = it
                return mapsList
            }
            return null
        }
    }

    suspend fun getById(uuid: String): Map? = mapWebClient.getById(uuid, language)
}