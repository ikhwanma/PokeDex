package com.ikhwanma.network.service

import com.ikhwanma.common.source.PagingResponse
import com.ikhwanma.common.source.Resource
import com.ikhwanma.network.response.listpokemon.PokemonListResponse
import com.ikhwanma.network.response.listpokemon.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonListPaging(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PagingResponse<Result>

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int ,
        @Query("offset") offset: Int
    ): PokemonListResponse

}