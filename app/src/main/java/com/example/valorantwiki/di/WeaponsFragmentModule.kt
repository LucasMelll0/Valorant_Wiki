package com.example.valorantwiki.di

import com.example.valorantwiki.repository.WeaponRepository
import com.example.valorantwiki.ui.recyclerview.adapter.WeaponsAdapter
import com.example.valorantwiki.viewmodel.weaponViewModel.WeaponListViewModel
import com.example.valorantwiki.webclient.WeaponWebClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weaponsFragmentModule = module {

    single {
        WeaponsAdapter()
    }

    single {
        WeaponWebClient()
    }

    single {
        WeaponRepository(get())
    }

    viewModel {
        WeaponListViewModel(get())
    }
}