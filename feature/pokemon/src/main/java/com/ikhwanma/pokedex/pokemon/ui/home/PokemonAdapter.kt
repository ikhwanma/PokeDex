package com.ikhwanma.pokedex.pokemon.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ikhwanma.base_ui.BaseRecyclerViewAdapter
import com.ikhwanma.model.Pokemon
import com.ikhwanma.pokedex.feature.pokemon.databinding.ItemPokemonBinding

class PokemonAdapter(private val onClickItem: (Int) -> Unit): BaseRecyclerViewAdapter<Pokemon, ItemPokemonBinding>() {

    private lateinit var context: Context

    override fun getViewBinding(parent: ViewGroup): ItemPokemonBinding {
        context = parent.context
        return ItemPokemonBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun bindItem(binding: ItemPokemonBinding, data: Pokemon, position: Int) {
        with(binding){
            Glide.with(context).load(data.imageUrl).into(ivPokemon)
            tvPokemonName.text = data.pokemonName
            root.setOnClickListener {
                onClickItem(data.id)
            }
        }
    }
}