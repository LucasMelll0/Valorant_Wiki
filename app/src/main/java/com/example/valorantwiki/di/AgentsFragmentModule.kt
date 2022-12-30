package com.example.valorantwiki.di

import com.example.valorantwiki.ui.recyclerview.adapter.AgentsAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val agentsFragmentModule = module {

    factory {
        AgentsAdapter(androidContext())
    }
}