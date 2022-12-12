package com.example.valorantwiki.viewmodel.maplistviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantwiki.repository.MapRepository

@Suppress("UNCHECKED_CAST")
class MapListViewModelFactory(
    private val application: Application,
    private val repository: MapRepository
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapListViewModel::class.java)){
            MapListViewModel(application, repository) as T
        }else {
            throw IllegalArgumentException("MapListViewModel Not Found")
        }
    }
}