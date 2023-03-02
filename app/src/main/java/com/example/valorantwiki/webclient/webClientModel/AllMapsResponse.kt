package com.example.valorantwiki.webclient.webClientModel

import com.example.valorantwiki.model.Map

data class MapResponseClass(
    private val uuid: String,
    private val name: String?,
    private val description: String?,
    private val image: String?,
    private val minimap: String?,
    private val gallery: List<String>?
){
    val map: Map
    get() = Map(
        uuid = this.uuid,
        name = this.name ?: "",
        description = this.description ?: "",
        miniMap = this.minimap ?: "",
        image = this.image ?: "",
        gallery = this.gallery ?: emptyList()
    )
}

