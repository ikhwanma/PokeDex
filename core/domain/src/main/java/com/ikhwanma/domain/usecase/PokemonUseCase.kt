package com.ikhwanma.domain.usecase

import androidx.paging.PagingData
import com.ikhwanma.common.source.Resource
import com.ikhwanma.database.entity.PokemonEntity
import com.ikhwanma.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getListPokemonPaging(): Flow<PagingData<Pokemon>>
    fun getPokemonList(limit: Int, offset: Int): Flow<Resource<List<Pokemon>>>

}