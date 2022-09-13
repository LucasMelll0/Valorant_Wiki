package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Map

data class AllMapsResponse(
    val status: Int,
    val data: List<MapResponseClass>
)

data class MapResponse(
    val status: Int,
    val data: MapResponseClass
)

data class MapResponseClass(
    private val uuid: String,
    private val displayName: String?,
    private val displayIcon: String?,
    private val splash: String?,
    private val callouts: List<Place>?
){
    val map: Map
    get() = Map(
        uuid = this.uuid,
        name = this.displayName ?: "",
        miniMap = this.displayIcon ?: "",
        image = this.splash ?: "",
        places = this.callouts ?: emptyList()
    )
}

class Place(
     val regionName: String,
     val superRegionName: String
)
