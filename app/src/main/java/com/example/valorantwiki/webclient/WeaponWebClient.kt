package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.webClientModel.AllWeaponResponse
import com.example.valorantwiki.webclient.webClientModel.WeaponResponse

class WeaponWebClient {

    private val weaponService = RetrofitInitializer().weaponService

    suspend fun getAll(language: String): AllWeaponResponse = weaponService.getAll(language)

    suspend fun getById(id: String, language: String): WeaponResponse =
        weaponService.getById(id, language)
}