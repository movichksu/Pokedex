package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName

data class GenerationIv(
    @SerialName("diamond-pearl")
    val diamond_pearl: DiamondPearl,
    @SerialName("heartgold-soulsilver")
    val heartgold_soulsilver: HeartgoldSoulsilver,
    val platinum: Platinum
)