package com.ikhwanma.pokedex.pokemon.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikhwanma.model.Pokemon
import com.ikhwanma.pokedex.feature.pokemon.databinding.ItemPokemonGridBinding

class PokemonPagingAdapter: PagingDataAdapter<Pokemon, PokemonPagingAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.pokemonName == newItem.pokemonName

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.pokemonName == newItem.pokemonName
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPokemonGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemPokemonGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Pokemon) {
            with(binding) {
                Glide.with(itemView).load(data.imageUrl).into(ivPokemon)
                tvPokemonName.text = data.pokemonName
            }
        }
    }
}