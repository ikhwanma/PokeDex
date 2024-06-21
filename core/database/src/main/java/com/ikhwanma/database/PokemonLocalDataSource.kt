package com.ikhwanma.database

import com.ikhwanma.database.dao.PokemonDao
import com.ikhwanma.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

class PokemonLocalDataSource(private val dao: PokemonDao) {
    suspend fun insertListPokemon(listPokemonEntity: List<PokemonEntity>) =
        dao.insertListPokemon(listPokemonEntity)

    fun getListPokemon(): Flow<List<PokemonEntity>> =
        dao.getListPokemon()
}