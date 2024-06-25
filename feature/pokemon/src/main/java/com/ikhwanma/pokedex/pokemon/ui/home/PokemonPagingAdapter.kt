package com.ikhwanma.pokedex.pokemon.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikhwanma.base_ui.BaseRecyclerViewPagingAdapter
import com.ikhwanma.model.Pokemon
import com.ikhwanma.pokedex.feature.pokemon.databinding.ItemPokemonGridBinding

class PokemonPagingAdapter: BaseRecyclerViewPagingAdapter<Pokemon, ItemPokemonGridBinding>() {

    private lateinit var context: Context

    override fun getViewBinding(parent: ViewGroup): ItemPokemonGridBinding {
        context = parent.context
        return ItemPokemonGridBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun bindItem(binding: ItemPokemonGridBinding, data: Pokemon, position: Int) {
        with(binding) {
            Glide.with(context).load(data.imageUrl).into(ivPokemon)
            tvPokemonName.text = data.pokemonName
        }
    }
}