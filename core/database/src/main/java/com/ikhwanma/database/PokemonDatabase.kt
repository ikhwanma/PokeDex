package com.ikhwanma.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikhwanma.database.dao.PokemonDao
import com.ikhwanma.database.entity.PokemonEntity

@Database(
    entities = [ PokemonEntity::class ],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}