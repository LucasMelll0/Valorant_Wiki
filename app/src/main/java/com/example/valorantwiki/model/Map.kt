package com.example.valorantwiki.model
data class Map(
    val uuid: String,
    val name: String,
    val description: String,
    val miniMap: String,
    val image: String,
    val gallery: List<String>

){
    override fun toString(): String {
        return "${this.name}\n"
    }
}