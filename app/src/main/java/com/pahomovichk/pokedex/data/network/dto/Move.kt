package com.pahomovichk.pokedex.data.network.dto

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)