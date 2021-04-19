package com.mscorp.meeple.model

import java.util.*

data class Event(
    val id: Int,
    val cords: Pair<Double, Double>,
    val title: String,
    val count: Int,
    val games: MutableList<BoardGame>,
    val levelPlayer: Int,
    val type: Int,
    val info: String,
    val date: Date,
    val access: Int,
    val members: MutableList<Pair<User, Status>>,
    val creatorID: Int
)