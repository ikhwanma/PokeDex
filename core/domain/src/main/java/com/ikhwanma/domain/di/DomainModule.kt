package com.ikhwanma.domain.di

import com.ikhwanma.domain.usecase.PokemonInteractor
import com.ikhwanma.domain.usecase.PokemonUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<PokemonUseCase>{ PokemonInteractor(get()) }
}