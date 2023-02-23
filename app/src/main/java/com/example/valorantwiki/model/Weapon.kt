package com.example.valorantwiki.model

import com.example.valorantwiki.webclient.webClientModel.WeaponShopData
import com.example.valorantwiki.webclient.webClientModel.WeaponSkin
import com.example.valorantwiki.webclient.webClientModel.WeaponStats

data class Weapon(
    val uuid: String,
    val name: String,
    val image: String,
    val stats: WeaponStats?,
    val shopData: WeaponShopData?,
    val skins: List<WeaponSkin>
)