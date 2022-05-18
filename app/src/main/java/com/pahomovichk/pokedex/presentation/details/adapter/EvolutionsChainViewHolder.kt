package com.pahomovichk.pokedex.presentation.details.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pahomovichk.pokedex.core.utils.getPokemonLargePngImage
import com.pahomovichk.pokedex.data.model.EvolutionChainItem
import com.pahomovichk.pokedex.databinding.ItemEvolutionChainBinding

class EvolutionsChainViewHolder(
    private val binding: ItemEvolutionChainBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: EvolutionChainItem) {
        with(binding) {
            parentName.text = item.parentName
            childName.text = item.childName
            loadImageIntoView(item.parentId, parentImage)
            loadImageIntoView(item.childId, childImage)
        }
    }

    private fun loadImageIntoView(id: Int, view: ImageView) {
        Glide
            .with(binding.root.context)
            .load(getPokemonLargePngImage(id.toString()))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(view)
    }
}