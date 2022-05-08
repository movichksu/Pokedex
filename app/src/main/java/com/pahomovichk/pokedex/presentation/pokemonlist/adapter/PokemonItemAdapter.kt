package com.pahomovichk.pokedex.presentation.pokemonlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pahomovichk.pokedex.data.model.PokemonItemEtity
import com.pahomovichk.pokedex.databinding.ItemPokemonCardBinding

class PokemonItemAdapter(
    private val onItemClickAction: (Int, Int) -> Unit
) : RecyclerView.Adapter<PokemonItemViewHolder>() {

    private val items = mutableListOf<PokemonItemEtity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        val view =
            ItemPokemonCardBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        return PokemonItemViewHolder(view, onItemClickAction)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    fun setItems(items: List<PokemonItemEtity>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }
}