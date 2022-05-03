package com.pahomovichk.pokedex.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    @SerialName("generation-i")
    val generation_i: GenerationI,
    @SerialName("generation-ii")
    val generation_ii: GenerationIi,
    @SerialName("generation-iii")
    val generation_iii: GenerationIii,
    @SerialName("generation-iv")
    val generation_iv: GenerationIv,
    @SerialName("generation-v")
    val generation_v: GenerationV,
    @SerialName("generation-vi")
    val generation_vi: GenerationVi,
    @SerialName("generation-vii")
    val generation_vii: GenerationVii,
    @SerialName("generation-viii")
    val generation_viii: GenerationViii
)