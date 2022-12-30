package com.example.valorantwiki.di

import com.example.valorantwiki.viewmodel.mapviewmodel.MapViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapActivityModule = module {
    viewModel {
        MapViewModel(androidApplication(), get())
    }
}