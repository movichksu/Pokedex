package com.pahomovichk.pokedex.presentation.pokemonlist.adapter

import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.core.ui.coloring.parseToColor
import com.pahomovichk.pokedex.core.utils.extensions.gone
import com.pahomovichk.pokedex.core.utils.extensions.visible
import com.pahomovichk.pokedex.data.model.PokemonItem
import com.pahomovichk.pokedex.data.network.dto.TypeX
import com.pahomovichk.pokedex.databinding.ItemPokemonBinding

class PokemonItemViewHolder(
    private val binding: ItemPokemonBinding,
    private val onItemClickAction: (Int) -> Unit,
    private val onHeartIconClickAction: (Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: PokemonItem) {
        with(binding) {
            pokemonNameText.text = item.pokemonName
            pokemonIdText.text =
                root.context.getString(R.string.pokemon_number_format, item.id)
            setPokemonImages(item)
            setPokemonTypes(item.types)
            root.setOnClickListener {
                onItemClickAction.invoke(item.id)
            }
            heartIcon.apply {
                setSelected(item.isFavourite)
                setOnClickListener {
                    if (isSelected) {
                        setSelected(false)
                    } else {
                        setSelected(true)
                        likeAnimation()
                    }
                    onHeartIconClickAction.invoke(item.id, isSelected)
                }
            }

        }
    }

    private fun setPokemonTypes(types: List<TypeX>) {
        with(binding) {
            if (types.isNotEmpty()) {
                pokemonTypeFirstText.apply {
                    text = types[0].name
                    visible()
                }
                if (types.size > 1) {
                    pokemonTypeSecondText.apply {
                        text = types[1].name
                        visible()
                    }
                } else { pokemonTypeSecondText.gone() }
            }
        }
    }

    private fun setPokemonImages(item: PokemonItem) {
        with(binding) {
            binding.pokemonCardContainer.setCardBackgroundColor(
                ContextCompat.getColor(root.context, R.color.type_dark)
            )
            Glide
                .with(itemView.context)
                .asBitmap()
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (resource != null) {
                            pokemonCardContainer.setCardBackgroundColor(
                                ContextCompat.getColor(
                                    root.context,
                                    item.dominantColor.parseToColor()
                                )
                            )
                        }
                        return false
                    }
                })
                .centerCrop()
                .into(pokemonImage)
        }
    }
}