package com.mscorp.meeple.model

import java.io.Serializable

internal data class BoardGames(
    val games: List<BoardGame>
) : Serializable

internal data class BoardGame(
    val id: Int,
    val name: String,
    val type: String,
    val countPlayer: String,
    val time: Int,
    val description: String,
    val pic: String
): Serializable