package com.example.valorantwiki.model

import com.example.valorantwiki.webclient.webClientModel.Place

class Map(
    val uuid: String,
    val name: String,
    val miniMap: String,
    val image: String,
    val places: List<Place>

){
    override fun toString(): String {
        return "${this.name}\n"
    }
}