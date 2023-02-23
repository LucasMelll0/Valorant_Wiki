package com.example.valorantwiki.webclient.services

import com.example.valorantwiki.webclient.webClientModel.AllWeaponResponse
import com.example.valorantwiki.webclient.webClientModel.WeaponResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeaponService {

    @GET("weapons")
    suspend fun getAll(@Query("language") language: String): AllWeaponResponse

    @GET("weapons/{uuid}?")
    suspend fun getById(
        @Path("uuid") uuid: String,
        @Query("language") language: String
    ): WeaponResponse
}