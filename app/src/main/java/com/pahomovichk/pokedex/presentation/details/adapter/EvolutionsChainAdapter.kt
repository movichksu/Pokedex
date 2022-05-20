package com.pahomovichk.pokedex.presentation.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pahomovichk.pokedex.data.model.EvolutionChainItem
import com.pahomovichk.pokedex.databinding.ItemEvolutionChainBinding

class EvolutionsChainAdapter: RecyclerView.Adapter<EvolutionsChainViewHolder>() {

    private val items = mutableListOf<EvolutionChainItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionsChainViewHolder {
        val view =
            ItemEvolutionChainBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        return EvolutionsChainViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EvolutionsChainViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<EvolutionChainItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}