package com.ikhwanma.network.response.listpokemon

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)