package com.example.valorantwiki.webclient

import android.util.Log
import com.example.valorantwiki.model.Map

class MapWebClient {
    private val mapService = RetrofitInicializer().mapService

    suspend fun getAll(): List<Map>? {
        return try {
            val mapsResponse = mapService.getAll()

            mapsResponse.data.map {
                it.map
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: ", e)
            null
        }
    }
}