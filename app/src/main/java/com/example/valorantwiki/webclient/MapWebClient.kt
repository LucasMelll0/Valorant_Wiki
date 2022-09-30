package com.example.valorantwiki.webclient

import android.util.Log
import com.example.valorantwiki.model.Map

class MapWebClient {
    private val mapService = RetrofitInicializer().mapService

    suspend fun getAll(): List<Map>? {
        return try {
            val mapsResponse = mapService.getAll()

            if (mapsResponse.status != 200) {
                null
            } else {
                mapsResponse.data.map {
                    it.map
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: ", e)
            null
        }
    }

    suspend fun getById(uuid: String, language: String): Map? {
        return try {
            val mapResponse = mapService.getById(uuid, language)

            mapResponse.data.map
        } catch (e: Exception) {
            Log.e(TAG, "getById: ", e)
            null
        }
    }
}