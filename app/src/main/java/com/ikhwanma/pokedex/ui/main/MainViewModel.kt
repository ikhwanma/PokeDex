package com.ikhwanma.pokedex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ikhwanma.domain.usecase.PokemonUseCase

class MainViewModel(private val pokemonUseCase: PokemonUseCase): ViewModel() {

    fun getListPokemon() = pokemonUseCase.getListPokemonPaging().asLiveData()

}