package com.ikhwanma.network.utils

import com.ikhwanma.common.utils.IMAGE_URL
import com.ikhwanma.common.utils.getIdPokemon
import com.ikhwanma.database.entity.PokemonEntity
import com.ikhwanma.model.Pokemon
import com.ikhwanma.network.response.listpokemon.Result


fun mapListResultToListPokemon(result: List<Result>) : List<Pokemon> =
    result.map {
        Pokemon(id = it.url.getIdPokemon().toInt(), pokemonName = it.name, imageUrl = "$IMAGE_URL${it.url.getIdPokemon()}.png")
    }

fun mapListPokemonEntityToListPokemon(result: List<PokemonEntity>): List<Pokemon> =
    result.map {
        Pokemon(id = it.id, pokemonName = it.pokemonName, imageUrl = it.imageUrl)
    }

fun mapListResultToListPokemonEntity(result: List<Result>): List<PokemonEntity> =
    result.map {
        PokemonEntity(id = it.url.getIdPokemon().toInt(), pokemonName = it.name, imageUrl = "$IMAGE_URL${it.url.getIdPokemon()}.png")
    }