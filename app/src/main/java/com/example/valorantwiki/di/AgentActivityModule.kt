package com.example.valorantwiki.di

import com.example.valorantwiki.viewmodel.agentviewmodel.AgentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agentActivityModule = module {

    viewModel {
        AgentViewModel(androidApplication(), get())
    }
}