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

   suspend fun getAll() : List<Map> = mapWebClient.getAll() ?: emptyList()

    suspend fun getById(uuid: String) : Map? = mapWebClient.getById(uuid, language)
}