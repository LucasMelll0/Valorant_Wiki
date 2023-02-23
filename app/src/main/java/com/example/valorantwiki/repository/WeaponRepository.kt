package com.example.valorantwiki.repository

import android.util.Log
import com.example.valorantwiki.model.Weapon
import com.example.valorantwiki.webclient.SUCCESS_CODE
import com.example.valorantwiki.webclient.TAG
import com.example.valorantwiki.webclient.WeaponWebClient

class WeaponRepository(
    private val webClient: WeaponWebClient
) {
    suspend fun getAll(language: String): List<Weapon>? {
        return try {
            val response = webClient.getAll(language)
            return if (response.status == SUCCESS_CODE) {
                val weapons = response.data.map { it.weapon }
                val weaponsSorted = weapons.sortedBy { it.name }
                weaponsSorted
            }else {
                null
            }
        }catch (e: Exception) {
            Log.w(TAG, "getAll: ", e)
            null
        }
    }

    suspend fun getById(uuid: String, language: String): Weapon? {
        return try {
            val response = webClient.getById(uuid, language)
            return if (response.status == SUCCESS_CODE) {
                val agent = response.data.weapon
                agent
            }else {
                null
            }
        }catch (e: Exception) {
            Log.w(TAG, "getById: ", e)
            null
        }
    }
}