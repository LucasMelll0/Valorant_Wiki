package com.example.valorantwiki.viewmodel.mapviewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.R
import com.example.valorantwiki.repository.MapRepository

class MapViewModel(application: Application, private val repository: MapRepository) : ViewModel() {

    private val language = application.getString(R.string.linguagem)
    internal val mapLiveData = MutableLiveData<com.example.valorantwiki.model.Map>()

    suspend fun getById(id: String){
        mapLiveData.value ?: run {
            val response = repository.getById(id, language)
            if (response.status == 200){
                val map = response.data.map
                mapLiveData.postValue(map)
            }
        }
    }

}