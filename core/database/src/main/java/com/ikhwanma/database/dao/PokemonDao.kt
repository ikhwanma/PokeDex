package com.ikhwanma.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ikhwanma.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPokemon(listPokemonEntity: List<PokemonEntity>)

    @Query("SELECT * FROM PokemonEntity")
    fun getListPokemon(): Flow<List<PokemonEntity>>

    @Query("DELETE FROM PokemonEntity")
    suspend fun clearListPokemon()
}