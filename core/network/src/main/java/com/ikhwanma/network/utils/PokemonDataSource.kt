package com.ikhwanma.network.utils

import com.ikhwanma.common.source.ApiResponse
import com.ikhwanma.common.source.PagingResponse
import com.ikhwanma.common.source.Resource
import com.ikhwanma.network.response.listpokemon.PokemonListResponse
import com.ikhwanma.network.response.listpokemon.Result
import com.ikhwanma.network.service.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonDataSource(private val apiService: PokemonService) {
    suspend fun getListPokemonPaging(limit: Int, offset: Int): PagingResponse<Result> =
        apiService.getPokemonListPaging(limit, offset)

    suspend fun getListPokemon(limit: Int, offset: Int): Flow<ApiResponse<PokemonListResponse>> =
        flow {
            try {
                val response = apiService.getPokemonList(limit, offset)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}