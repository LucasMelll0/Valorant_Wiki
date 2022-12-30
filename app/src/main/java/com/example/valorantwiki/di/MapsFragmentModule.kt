package com.example.valorantwiki.di

import com.example.valorantwiki.ui.recyclerview.adapter.MapsAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mapsFragmentModule = module {

    factory {
        MapsAdapter(androidContext())
    }
}