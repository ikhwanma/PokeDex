package com.ikhwanma.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val pokemonName: String,
    val imageUrl: String
)
