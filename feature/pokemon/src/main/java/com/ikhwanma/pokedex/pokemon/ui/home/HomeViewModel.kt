package com.ikhwanma.pokedex.pokemon.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ikhwanma.domain.usecase.PokemonUseCase

class HomeViewModel(
    private val pokemonUseCase: PokemonUseCase
): ViewModel(){
    fun getListPokemonPaging() = pokemonUseCase.getListPokemonPaging().asLiveData()
    fun getListPokemon(limit:Int, offset: Int) = pokemonUseCase.getPokemonList(limit, offset).asLiveData()
}