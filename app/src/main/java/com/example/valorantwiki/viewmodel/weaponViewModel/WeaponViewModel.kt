package com.example.valorantwiki.viewmodel.weaponViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valorantwiki.model.Weapon
import com.example.valorantwiki.repository.WeaponRepository

class WeaponViewModel(
    private val repository: WeaponRepository
) : ViewModel() {

    private var _weapon = MutableLiveData<Weapon?>(null)
    internal val weapon: LiveData<Weapon?> = _weapon

    suspend fun getById(uuid: String, language: String) {
        _weapon.value ?: run {
            val weapon = repository.getById(uuid, language)
            _weapon.postValue(weapon)
        }
    }

}