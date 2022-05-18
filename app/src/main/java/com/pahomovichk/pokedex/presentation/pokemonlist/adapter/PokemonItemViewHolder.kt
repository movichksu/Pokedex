package com.pahomovichk.pokedex.presentation.pokemonlist.adapter

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pahomovichk.pokedex.R
import com.pahomovichk.pokedex.data.model.PokemonItem
import com.pahomovichk.pokedex.databinding.ItemPokemonBinding

class PokemonItemViewHolder(
    private val binding: ItemPokemonBinding,
    private val onItemClickAction: (Int) -> Unit,
    private val onHeartIconClickAction: (Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var dominantColor: Int = Color.GRAY

    fun bindView(item: PokemonItem) {
        with(binding) {
            pokemonNameText.text = item.pokemonName
            pokemonIdText.text =
                root.context.getString(R.string.pokemon_number_format, item.id)
            setPokemonImages(item)
            root.setOnClickListener {
                onItemClickAction.invoke(item.id)
            }
            val heartIconRes = if (item.isFavourite) {
                R.drawable.ic_heart_filled
            } else {
                R.drawable.ic_heart
            }
            heartIcon.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(root.context, heartIconRes)
                )
                setOnClickListener {
                    onHeartIconClickAction.invoke(item.id, !item.isFavourite)
                }
            }

        }
    }

    private fun setPokemonImages(item: PokemonItem) {
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
                        calcDominantColor(resource)
                    }
                    return false
                }
            })
            .centerCrop()
            .into(binding.pokemonImage)
    }

    fun calcDominantColor(drawable: Bitmap) {
        val bmp = drawable.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate {
            it?.let { palette ->
                palette.dominantSwatch?.rgb?.let { colorValue ->
                    dominantColor = colorValue
                    binding.pokemonCardContainer.setCardBackgroundColor(dominantColor)
                }
            }
        }
    }
}