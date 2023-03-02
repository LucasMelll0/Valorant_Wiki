package com.example.valorantwiki.webclient

import com.example.valorantwiki.webclient.services.MapService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInitializerMaps {

    private val baseUrl =  "http://ec2-34-234-223-213.compute-1.amazonaws.com:8080/api/"

    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val mapService: MapService = retrofit.create(MapService::class.java)
}