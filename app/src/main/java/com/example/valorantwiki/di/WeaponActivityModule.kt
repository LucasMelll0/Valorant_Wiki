package com.example.valorantwiki.di

import com.example.valorantwiki.ui.recyclerview.adapter.DamageRangesAdapter
import com.example.valorantwiki.ui.recyclerview.adapter.WeaponSkinsAdapter
import com.example.valorantwiki.viewmodel.weaponViewModel.WeaponViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weaponActivityModule = module {

    single {
        DamageRangesAdapter()
    }

    single {
        WeaponSkinsAdapter()
    }

    viewModel {
        WeaponViewModel(get())
    }
}