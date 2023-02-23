package com.example.valorantwiki.viewmodel.weaponViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.model.Weapon
import com.example.valorantwiki.repository.WeaponRepository

class WeaponListViewModel(
    private val repository: WeaponRepository
) : ViewModel() {
    companion object {
        private var weaponsCompanion: MutableLiveData<List<Weapon>> = MutableLiveData(emptyList())
    }

    internal val weapons: LiveData<List<Weapon>> = weaponsCompanion

    suspend fun getAll(language: String) {
        weaponsCompanion.value?.let { liveDataValue ->
            if (liveDataValue.isEmpty()) {
                val weapons = repository.getAll(language)
                weapons?.let {
                    weaponsCompanion.postValue(it)
                }
            }
        }
    }

    fun searchByName(query: String): List<Weapon> {
        return weaponsCompanion.value?.let { weapons ->
            weapons.filter { weapon ->
                weapon.name.startsWith(query, true)
            }
        } ?: emptyList()
    }
}