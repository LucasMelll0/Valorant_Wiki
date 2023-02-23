package com.example.valorantwiki.viewmodel.mapviewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.R
import com.example.valorantwiki.repository.MapRepository

class MapListViewModel(application: Application, private val repository: MapRepository) :
    ViewModel() {

    private val language = application.getString(R.string.linguagem)


    companion object {
        private val mapsLiveDataCompanion = MutableLiveData<List<com.example.valorantwiki.model.Map>>()
    }

    internal val mapsLiveData: LiveData<List<com.example.valorantwiki.model.Map>> = mapsLiveDataCompanion

    suspend fun getAll() {
        mapsLiveData.value ?: run {
            val response = repository.getAll(language)
            if(response.status == 200){
                val maps = response.data.map { map ->
                    map.map
                }
                mapsLiveDataCompanion.postValue(maps)

            }
        }
    }
}