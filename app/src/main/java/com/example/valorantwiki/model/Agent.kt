package com.example.valorantwiki.model

import com.example.valorantwiki.webclient.webClientModel.Abilitie
import com.example.valorantwiki.webclient.webClientModel.Role

data class Agent(
    val uuid: String,
    val displayName: String,
    val description: String,
    val fullPortrait: String,
    val role: Role,
    val abilities: List<Abilitie>
){
    override fun toString(): String {
        return "$displayName \n"
    }
}