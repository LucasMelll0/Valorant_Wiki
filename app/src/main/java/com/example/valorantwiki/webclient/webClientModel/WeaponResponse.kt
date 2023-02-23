package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Weapon

data class WeaponResponse(
    val status: Int,
    val data: WeaponResponseClass
)

data class AllWeaponResponse(
    val status: Int,
    val data: List<WeaponResponseClass>
)

data class WeaponResponseClass(
    private val uuid: String,
    private val displayName: String?,
    private val displayIcon: String?,
    private val weaponStats: WeaponStats?,
    private val shopData: WeaponShopData?,
    private val skins: List<WeaponSkin>?
) {
    val weapon: Weapon
    get() = Weapon(
        uuid = this.uuid,
        name = this.displayName ?: "",
        image = this.displayIcon ?: "",
        stats = this.weaponStats,
        shopData = this.shopData,
        skins = this.skins ?: emptyList()
    )
}

data class WeaponSkin(
    val displayName: String?,
    val displayIcon: String?
)

data class WeaponShopData(
    val cost: Double,
    val categoryText: String
)

data class DamageRange(
    val rangeStartMeters: Int,
    val rangeEndMeters: Int,
    val headDamage: Float,
    val bodyDamage: Float,
    val legDamage: Float
)

data class WeaponStats(
    val fireRate: Float,
    val magazineSize: Int,
    val runSpeedMultiplier: Float,
    val equipTimeSeconds: Float,
    val reloadTimeSeconds: Float,
    val damageRanges: List<DamageRange>
)
