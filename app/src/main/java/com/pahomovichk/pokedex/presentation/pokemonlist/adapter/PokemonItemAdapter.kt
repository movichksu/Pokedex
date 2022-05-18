package com.pahomovichk.pokedex.presentation.pokemonlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pahomovichk.pokedex.data.model.PokemonItem
import com.pahomovichk.pokedex.databinding.ItemPokemonBinding

class PokemonItemAdapter(
    private val onItemClickAction: (Int) -> Unit,
    private val onHeartIconClickAction: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<PokemonItemViewHolder>() {

    private val items = mutableListOf<PokemonItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        val view =
            ItemPokemonBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        return PokemonItemViewHolder(view, onItemClickAction, onHeartIconClickAction)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<PokemonItem>) {
        if (this.items.isEmpty() || this.items.size != newItems.size) {
            this.items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            this.items.forEachIndexed { index, _ ->
                if (this.items[index] != newItems[index]) {
                    items.set(index, newItems[index])
                    notifyItemChanged(index)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavouriteItems(newItems: List<PokemonItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun changeItem(id: Int, isFavourite: Boolean) {
        val index = items.indexOfFirst { it.id == id }
        items[index] = items[index].copy(isFavourite = isFavourite)
        notifyItemChanged(index)
    }
}