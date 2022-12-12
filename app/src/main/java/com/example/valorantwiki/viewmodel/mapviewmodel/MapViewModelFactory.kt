package com.example.valorantwiki.viewmodel.mapviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.valorantwiki.repository.MapRepository

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val application: Application,
    private val repository: MapRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(application, repository) as T
        }else {
            throw IllegalArgumentException("MapViewModel Not Found")
        }
    }
}