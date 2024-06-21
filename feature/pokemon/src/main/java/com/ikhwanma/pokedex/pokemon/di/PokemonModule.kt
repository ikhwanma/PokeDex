package com.ikhwanma.pokedex.pokemon.di

import com.ikhwanma.pokedex.pokemon.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonModule = module {
    viewModel { HomeViewModel(get()) }
}