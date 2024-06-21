package com.ikhwanma.domain.usecase

import androidx.paging.PagingData
import com.ikhwanma.common.source.Resource
import com.ikhwanma.database.entity.PokemonEntity
import com.ikhwanma.model.Pokemon
import com.ikhwanma.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonInteractor(private val repository: IPokemonRepository): PokemonUseCase {
    override fun getListPokemonPaging(): Flow<PagingData<Pokemon>> =
        repository.getPokemonListPaging()

    override fun getPokemonList(limit: Int, offset: Int): Flow<Resource<List<Pokemon>>> =
        repository.getPokemonList(limit, offset)
}