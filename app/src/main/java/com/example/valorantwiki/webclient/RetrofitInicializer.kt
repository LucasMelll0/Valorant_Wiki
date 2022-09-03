package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.services.AgentService
import com.example.valorantwiki.webclient.services.MapService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class RetrofitInicializer {

    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    private val retrofit : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://valorant-api.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val agentService = retrofit.create(AgentService::class.java)
    val mapService = retrofit.create(MapService::class.java)
}