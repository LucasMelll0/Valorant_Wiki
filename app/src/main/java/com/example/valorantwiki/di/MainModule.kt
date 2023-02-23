package com.example.valorantwiki.di

import com.example.valorantwiki.repository.AgentRepository
import com.example.valorantwiki.repository.MapRepository
import com.example.valorantwiki.viewmodel.agentviewmodel.AgentListViewModel
import com.example.valorantwiki.viewmodel.mapviewmodel.MapListViewModel
import com.example.valorantwiki.webclient.AgentWebClient
import com.example.valorantwiki.webclient.MapWebClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory {
        AgentRepository(AgentWebClient())
    }

    factory {
        MapRepository(MapWebClient())
    }

    viewModel {
        AgentListViewModel(get())
    }

    viewModel {
        MapListViewModel(androidApplication(), get())
    }

}