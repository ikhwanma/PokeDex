package com.ikhwanma.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ikhwanma.common.source.ApiResponse
import com.ikhwanma.common.source.NetworkBoundResource
import com.ikhwanma.model.Pokemon
import com.ikhwanma.domain.repository.IPokemonRepository
import com.ikhwanma.network.utils.PokemonDataSource
import com.ikhwanma.network.response.listpokemon.Result
import com.ikhwanma.common.source.PagingRemoteSource
import com.ikhwanma.common.source.PagingResponse
import com.ikhwanma.common.source.Resource
import com.ikhwanma.database.PokemonLocalDataSource
import com.ikhwanma.database.entity.PokemonEntity
import com.ikhwanma.network.response.listpokemon.PokemonListResponse
import com.ikhwanma.network.utils.mapListPokemonEntityToListPokemon
import com.ikhwanma.network.utils.mapListResultToListPokemon
import com.ikhwanma.network.utils.mapListResultToListPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonDataSource: PokemonDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource
): IPokemonRepository {

    override fun getPokemonListPaging(): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(20, enablePlaceholders = true)
        ){
            object : PagingRemoteSource<Pokemon, Result>(){
                override suspend fun createCall(
                    limit: Int,
                    offset: Int
                ): PagingResponse<Result> =
                    pokemonDataSource.getListPokemonPaging(limit, offset)

                override fun mapToResult(response: List<Result>): List<Pokemon> =
                    mapListResultToListPokemon(response)
            }
        }.flow

    override fun getPokemonList(limit: Int, offset: Int): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, PokemonListResponse>(){
            override fun loadFromDB(): Flow<List<Pokemon>> =
                pokemonLocalDataSource.getListPokemon().map {
                    mapListPokemonEntityToListPokemon(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<PokemonListResponse>> =
                pokemonDataSource.getListPokemon(limit, offset)

            override suspend fun saveCallResult(data: PokemonListResponse) =
                pokemonLocalDataSource.insertListPokemon(mapListResultToListPokemonEntity(data.results))


            override fun shouldFetch(data: List<Pokemon>?): Boolean =
                data == null || data.isEmpty()

        }.asFlow()
}