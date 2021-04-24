package com.mscorp.meeple.model

import java.io.Serializable

data class Event(
    val id: Int,
    val title: String,
//    val cords: Pair<Double, Double>,
    val count: Int,
    val games: List<Int>,
    val playersLevel: Int,
    val type: Int,
    val info: String,
    val date: Int,
    val access: Int,
    val members: List<Int>,
    val creatorId: Int
): Serializable