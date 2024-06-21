package com.ikhwanma.database.di

import androidx.room.Room
import com.ikhwanma.database.PokemonDatabase
import com.ikhwanma.database.PokemonLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "Pokedex.db"
        ).fallbackToDestructiveMigration().build()
    }
    factory {
        get<PokemonDatabase>().pokemonDao()
    }
}

val localDataSourceModule = module {
    single { PokemonLocalDataSource(get()) }
}